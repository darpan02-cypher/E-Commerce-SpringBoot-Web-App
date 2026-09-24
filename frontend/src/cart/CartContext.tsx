import { createContext, useContext, useEffect, useState, type ReactNode } from 'react';
import { CartApi } from '../api/endpoints';
import type { Cart } from '../api/types';

const CART_ID_KEY = 'ecom.cartId';

function getStoredCartId(): number {
  const raw = localStorage.getItem(CART_ID_KEY);
  return raw ? Number(raw) : 0;
}

interface CartContextValue {
  cart: Cart | null;
  itemCount: number;
  loading: boolean;
  refresh: () => Promise<void>;
  addItem: (productId: number, quantity: number) => Promise<void>;
  removeItem: (productId: number, quantity: number) => Promise<void>;
}

const CartContext = createContext<CartContextValue | null>(null);

export function CartProvider({ children }: { children: ReactNode }) {
  const [cartId, setCartId] = useState<number>(getStoredCartId);
  const [cart, setCart] = useState<Cart | null>(null);
  const [loading, setLoading] = useState(false);

  function persistCart(next: Cart) {
    setCart(next);
    setCartId(next.id);
    localStorage.setItem(CART_ID_KEY, String(next.id));
  }

  async function refresh() {
    if (!cartId) return;
    setLoading(true);
    try {
      const next = await CartApi.get(cartId);
      setCart(next);
    } catch {
      // cart id is stale (e.g. never created yet) - stay empty until the next add
      setCart(null);
    } finally {
      setLoading(false);
    }
  }

  async function addItem(productId: number, quantity: number) {
    setLoading(true);
    try {
      const next = await CartApi.add(cartId, { productId, quantity });
      persistCart(next);
    } finally {
      setLoading(false);
    }
  }

  async function removeItem(productId: number, quantity: number) {
    if (!cartId) return;
    setLoading(true);
    try {
      const next = await CartApi.remove(cartId, { productId, quantity });
      persistCart(next);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    refresh();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const itemCount = cart?.items.reduce((sum, item) => sum + item.quantity, 0) ?? 0;

  return (
    <CartContext.Provider value={{ cart, itemCount, loading, refresh, addItem, removeItem }}>
      {children}
    </CartContext.Provider>
  );
}

export function useCart(): CartContextValue {
  const ctx = useContext(CartContext);
  if (!ctx) throw new Error('useCart must be used within CartProvider');
  return ctx;
}
