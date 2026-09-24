export interface ProductResponse {
  id: number;
  name: string;
  description: string;
  price: number;
}

export interface ProductRequest {
  name: string;
  description: string;
  price: number;
}

export interface CartItem {
  id: number;
  product: ProductResponse;
  quantity: number;
}

export interface Cart {
  id: number;
  items: CartItem[];
}

export interface CartRequest {
  productId: number;
  quantity: number;
}

export interface OrderItemResponse {
  id: number;
  product: ProductResponse;
  quantity: number;
  priceAtPurchase: number;
}

export interface OrderResponse {
  id: number;
  createdAt: string;
  totalAmount: number;
  status: string;
  items: OrderItemResponse[];
}

export interface AuthRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  token: string;
}

export interface LogSummaryResponse {
  logsAnalyzed: number;
  errors: number;
  warnings: number;
  incidents: number;
  categories: Record<string, number>;
}

export type IncidentCategory = string;
export type LogSeverity = string;

export interface IncidentResponse {
  incidentId: string;
  category: IncidentCategory;
  severity: LogSeverity;
  firstSeen: string;
  lastSeen: string;
  occurrenceCount: number;
  affectedService: string;
  affectedEndpoint: string;
  representativeError: string;
  probableCause: string;
  recommendation: string;
  requestIds: string[];
}

export interface LogAnalysisResponse {
  totalLogsAnalyzed: number;
  incidentsDetected: number;
  incidents: IncidentResponse[];
}

export interface ApiErrorBody {
  timestamp?: string;
  status?: number;
  error?: string;
  message?: string;
  errors?: Record<string, string>;
}
