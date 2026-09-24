import { apiFetch } from './client';
import type {
  AuthRequest,
  AuthResponse,
  Cart,
  CartRequest,
  LogAnalysisResponse,
  LogSummaryResponse,
  OrderResponse,
  ProductResponse,
} from './types';

export const AuthApi = {
  login: (req: AuthRequest) =>
    apiFetch<AuthResponse>('/auth/login', { method: 'POST', body: JSON.stringify(req) }),
};

export const ProductApi = {
  list: () => apiFetch<ProductResponse[]>('/products'),
  search: (keyword: string) =>
    apiFetch<ProductResponse[]>(`/products/search?keyword=${encodeURIComponent(keyword)}`),
};

export const CartApi = {
  get: (cartId: number) => apiFetch<Cart>(`/api/cart/${cartId}`),
  add: (cartId: number, req: CartRequest) =>
    apiFetch<Cart>(`/api/cart/${cartId}/add`, { method: 'POST', body: JSON.stringify(req) }),
  remove: (cartId: number, req: CartRequest) =>
    apiFetch<Cart>(`/api/cart/${cartId}/remove`, { method: 'POST', body: JSON.stringify(req) }),
};

export const OrderApi = {
  list: () => apiFetch<OrderResponse[]>('/orders'),
  get: (id: number) => apiFetch<OrderResponse>(`/orders/${id}`),
  createFromCart: (cartId: number) => apiFetch<OrderResponse>(`/orders/${cartId}`, { method: 'POST' }),
  cancel: (id: number) => apiFetch<void>(`/orders/${id}`, { method: 'DELETE' }),
};

export const LogIntelligenceApi = {
  summary: () => apiFetch<LogSummaryResponse>('/api/log-intelligence/summary'),
  analyze: () => apiFetch<LogAnalysisResponse>('/api/log-intelligence/analyze'),
};
