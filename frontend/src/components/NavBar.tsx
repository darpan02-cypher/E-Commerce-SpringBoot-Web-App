import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../auth/AuthContext';
import { useCart } from '../cart/CartContext';

export function NavBar() {
  const { isAuthenticated, logout } = useAuth();
  const { itemCount } = useCart();
  const navigate = useNavigate();

  function handleLogout() {
    logout();
    navigate('/login');
  }

  return (
    <nav className="flex items-center justify-between border-b border-gray-200 px-6 py-3">
      <Link to="/products" className="text-lg font-semibold text-gray-900">
        e-com
      </Link>
      {isAuthenticated && (
        <div className="flex items-center gap-6 text-sm text-gray-600">
          <Link to="/products" className="hover:text-gray-900">Products</Link>
          <Link to="/orders" className="hover:text-gray-900">Orders</Link>
          <Link to="/log-intelligence" className="hover:text-gray-900">Log Intelligence</Link>
          <Link to="/cart" className="relative hover:text-gray-900">
            Cart
            {itemCount > 0 && (
              <span className="absolute -right-3 -top-2 rounded-full bg-gray-900 px-1.5 py-0.5 text-xs text-white">
                {itemCount}
              </span>
            )}
          </Link>
          <button onClick={handleLogout} className="text-gray-500 hover:text-gray-900">
            Log out
          </button>
        </div>
      )}
    </nav>
  );
}
