import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { OrderApi } from '../api/endpoints';
import type { OrderResponse } from '../api/types';

export function OrdersPage() {
  const [orders, setOrders] = useState<OrderResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    OrderApi.list()
      .then(setOrders)
      .catch(() => setError('Could not load orders.'))
      .finally(() => setLoading(false));
  }, []);

  return (
    <div className="mx-auto max-w-3xl px-6 py-8">
      <h1 className="text-xl font-semibold text-gray-900">Order history</h1>

      {loading && <p className="mt-6 text-sm text-gray-500">Loading…</p>}
      {error && <p className="mt-6 text-sm text-red-600">{error}</p>}
      {!loading && !error && orders.length === 0 && (
        <p className="mt-6 text-sm text-gray-500">No orders yet.</p>
      )}

      <div className="mt-6 flex flex-col gap-3">
        {orders.map((order) => (
          <Link
            key={order.id}
            to={`/orders/${order.id}`}
            className="flex items-center justify-between rounded-lg border border-gray-200 p-4 hover:bg-gray-50"
          >
            <div>
              <p className="font-medium text-gray-900">Order #{order.id}</p>
              <p className="text-sm text-gray-500">
                {new Date(order.createdAt).toLocaleString()} · {order.status}
              </p>
            </div>
            <span className="font-semibold text-gray-900">${order.totalAmount.toFixed(2)}</span>
          </Link>
        ))}
      </div>
    </div>
  );
}
