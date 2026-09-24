import { useState } from 'react';
import type { ProductResponse } from '../api/types';
import { useCart } from '../cart/CartContext';

export function ProductCard({ product }: { product: ProductResponse }) {
  const { addItem } = useCart();
  const [adding, setAdding] = useState(false);
  const [added, setAdded] = useState(false);

  async function handleAdd() {
    setAdding(true);
    try {
      await addItem(product.id, 1);
      setAdded(true);
      setTimeout(() => setAdded(false), 1200);
    } finally {
      setAdding(false);
    }
  }

  return (
    <div className="flex flex-col justify-between rounded-lg border border-gray-200 p-4">
      <div>
        <h3 className="font-medium text-gray-900">{product.name}</h3>
        <p className="mt-1 text-sm text-gray-500">{product.description}</p>
      </div>
      <div className="mt-4 flex items-center justify-between">
        <span className="font-semibold text-gray-900">${product.price.toFixed(2)}</span>
        <button
          onClick={handleAdd}
          disabled={adding}
          className="rounded-md bg-gray-900 px-3 py-1.5 text-sm text-white hover:bg-gray-700 disabled:opacity-50"
        >
          {added ? 'Added' : adding ? 'Adding…' : 'Add to cart'}
        </button>
      </div>
    </div>
  );
}
