import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../cart/CartContext';
import { OrderApi } from '../api/endpoints';
import { ApiError } from '../api/client';

export function CartPage() {
  const { cart, loading, removeItem, refresh } = useCart();
  const navigate = useNavigate();
  const [placingOrder, setPlacingOrder] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const total =
    cart?.items.reduce((sum, item) => sum + item.product.price * item.quantity, 0) ?? 0;

  async function handleCheckout() {
    if (!cart) return;
    setPlacingOrder(true);
    setError(null);
    try {
      const order = await OrderApi.createFromCart(cart.id);
      await refresh();
      navigate(`/orders/${order.id}`);
    } catch (err) {
      setError(err instanceof ApiError && err.body?.message ? err.body.message : 'Could not place order.');
    } finally {
      setPlacingOrder(false);
    }
  }

  return (
    <div className="mx-auto max-w-3xl px-6 py-8">
      <h1 className="text-xl font-semibold text-gray-900">Your cart</h1>

      {loading && <p className="mt-6 text-sm text-gray-500">Loading…</p>}

      {!loading && (!cart || cart.items.length === 0) && (
        <p className="mt-6 text-sm text-gray-500">Your cart is empty.</p>
      )}

      {!loading && cart && cart.items.length > 0 && (
        <div className="mt-6 flex flex-col gap-3">
          {cart.items.map((item) => (
            <div
              key={item.id}
              className="flex items-center justify-between rounded-lg border border-gray-200 p-4"
            >
              <div>
                <p className="font-medium text-gray-900">{item.product.name}</p>
                <p className="text-sm text-gray-500">
                  {item.quantity} × ${item.product.price.toFixed(2)}
                </p>
              </div>
              <button
                onClick={() => removeItem(item.product.id, item.quantity)}
                className="text-sm text-gray-500 hover:text-red-600"
              >
                Remove
              </button>
            </div>
          ))}

          <div className="mt-4 flex items-center justify-between border-t border-gray-200 pt-4">
            <span className="font-semibold text-gray-900">Total: ${total.toFixed(2)}</span>
            <button
              onClick={handleCheckout}
              disabled={placingOrder}
              className="rounded-md bg-gray-900 px-4 py-2 text-sm text-white hover:bg-gray-700 disabled:opacity-50"
            >
              {placingOrder ? 'Placing order…' : 'Place order'}
            </button>
          </div>
          {error && <p className="text-sm text-red-600">{error}</p>}
        </div>
      )}
    </div>
  );
}
