import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { OrderApi } from '../api/endpoints';
import type { OrderResponse } from '../api/types';

export function OrderDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [order, setOrder] = useState<OrderResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [cancelling, setCancelling] = useState(false);

  useEffect(() => {
    if (!id) return;
    OrderApi.get(Number(id))
      .then(setOrder)
      .catch(() => setError('Order not found.'))
      .finally(() => setLoading(false));
  }, [id]);

  async function handleCancel() {
    if (!order) return;
    setCancelling(true);
    try {
      await OrderApi.cancel(order.id);
      navigate('/orders');
    } finally {
      setCancelling(false);
    }
  }

  if (loading) return <p className="mx-auto max-w-3xl px-6 py-8 text-sm text-gray-500">Loading…</p>;
  if (error || !order) return <p className="mx-auto max-w-3xl px-6 py-8 text-sm text-red-600">{error}</p>;

  return (
    <div className="mx-auto max-w-3xl px-6 py-8">
      <h1 className="text-xl font-semibold text-gray-900">Order #{order.id}</h1>
      <p className="mt-1 text-sm text-gray-500">
        {new Date(order.createdAt).toLocaleString()} · {order.status}
      </p>

      <div className="mt-6 flex flex-col gap-3">
        {order.items.map((item) => (
          <div key={item.id} className="flex items-center justify-between rounded-lg border border-gray-200 p-4">
            <div>
              <p className="font-medium text-gray-900">{item.product.name}</p>
              <p className="text-sm text-gray-500">
                {item.quantity} × ${item.priceAtPurchase.toFixed(2)}
              </p>
            </div>
            <span className="font-semibold text-gray-900">
              ${(item.quantity * item.priceAtPurchase).toFixed(2)}
            </span>
          </div>
        ))}
      </div>

      <div className="mt-6 flex items-center justify-between border-t border-gray-200 pt-4">
        <span className="font-semibold text-gray-900">Total: ${order.totalAmount.toFixed(2)}</span>
        <button
          onClick={handleCancel}
          disabled={cancelling}
          className="rounded-md border border-gray-300 px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 disabled:opacity-50"
        >
          {cancelling ? 'Cancelling…' : 'Cancel order'}
        </button>
      </div>
    </div>
  );
}
