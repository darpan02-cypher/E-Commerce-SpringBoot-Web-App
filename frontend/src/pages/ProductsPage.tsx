import { useEffect, useState } from 'react';
import { ProductApi } from '../api/endpoints';
import type { ProductResponse } from '../api/types';
import { ProductCard } from '../components/ProductCard';

export function ProductsPage() {
  const [products, setProducts] = useState<ProductResponse[]>([]);
  const [keyword, setKeyword] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  async function load(searchKeyword?: string) {
    setLoading(true);
    setError(null);
    try {
      const result = searchKeyword ? await ProductApi.search(searchKeyword) : await ProductApi.list();
      setProducts(result);
    } catch {
      setError('Could not load products.');
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    load();
  }, []);

  function handleSearch(e: React.FormEvent) {
    e.preventDefault();
    load(keyword || undefined);
  }

  return (
    <div className="mx-auto max-w-5xl px-6 py-8">
      <div className="flex items-center justify-between">
        <h1 className="text-xl font-semibold text-gray-900">Products</h1>
        <form onSubmit={handleSearch} className="flex gap-2">
          <input
            type="text"
            placeholder="Search products…"
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
            className="rounded-md border border-gray-300 px-3 py-1.5 text-sm"
          />
          <button type="submit" className="rounded-md border border-gray-300 px-3 py-1.5 text-sm hover:bg-gray-50">
            Search
          </button>
        </form>
      </div>

      {loading && <p className="mt-6 text-sm text-gray-500">Loading…</p>}
      {error && <p className="mt-6 text-sm text-red-600">{error}</p>}
      {!loading && !error && products.length === 0 && (
        <p className="mt-6 text-sm text-gray-500">No products found.</p>
      )}

      <div className="mt-6 grid grid-cols-1 gap-4 sm:grid-cols-2 md:grid-cols-3">
        {products.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))}
      </div>
    </div>
  );
}
