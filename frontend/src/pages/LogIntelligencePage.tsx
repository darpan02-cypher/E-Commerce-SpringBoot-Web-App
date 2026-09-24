import { useEffect, useState } from 'react';
import { LogIntelligenceApi } from '../api/endpoints';
import type { IncidentResponse, LogSummaryResponse } from '../api/types';

const SEVERITY_COLOR: Record<string, string> = {
  CRITICAL: 'bg-red-100 text-red-700',
  HIGH: 'bg-orange-100 text-orange-700',
  MEDIUM: 'bg-yellow-100 text-yellow-700',
  LOW: 'bg-gray-100 text-gray-700',
};

function StatTile({ label, value }: { label: string; value: number }) {
  return (
    <div className="rounded-lg border border-gray-200 p-4">
      <p className="text-sm text-gray-500">{label}</p>
      <p className="mt-1 text-2xl font-semibold text-gray-900">{value}</p>
    </div>
  );
}

export function LogIntelligencePage() {
  const [summary, setSummary] = useState<LogSummaryResponse | null>(null);
  const [incidents, setIncidents] = useState<IncidentResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    Promise.all([LogIntelligenceApi.summary(), LogIntelligenceApi.analyze()])
      .then(([summaryRes, analysisRes]) => {
        setSummary(summaryRes);
        setIncidents(analysisRes.incidents);
      })
      .catch(() => setError('Could not load log intelligence data.'))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p className="mx-auto max-w-4xl px-6 py-8 text-sm text-gray-500">Loading…</p>;
  if (error) return <p className="mx-auto max-w-4xl px-6 py-8 text-sm text-red-600">{error}</p>;

  return (
    <div className="mx-auto max-w-4xl px-6 py-8">
      <h1 className="text-xl font-semibold text-gray-900">Log intelligence</h1>

      {summary && (
        <div className="mt-6 grid grid-cols-2 gap-4 sm:grid-cols-4">
          <StatTile label="Logs analyzed" value={summary.logsAnalyzed} />
          <StatTile label="Errors" value={summary.errors} />
          <StatTile label="Warnings" value={summary.warnings} />
          <StatTile label="Incidents" value={summary.incidents} />
        </div>
      )}

      {summary && Object.keys(summary.categories).length > 0 && (
        <div className="mt-6">
          <h2 className="text-sm font-medium text-gray-700">By category</h2>
          <div className="mt-2 flex flex-wrap gap-2">
            {Object.entries(summary.categories).map(([category, count]) => (
              <span
                key={category}
                className="rounded-full bg-gray-100 px-3 py-1 text-sm text-gray-700"
              >
                {category}: {count}
              </span>
            ))}
          </div>
        </div>
      )}

      <h2 className="mt-8 text-sm font-medium text-gray-700">Detected incidents</h2>
      {incidents.length === 0 && <p className="mt-2 text-sm text-gray-500">No incidents detected.</p>}
      <div className="mt-2 flex flex-col gap-3">
        {incidents.map((incident) => (
          <div key={incident.incidentId} className="rounded-lg border border-gray-200 p-4">
            <div className="flex items-center justify-between">
              <p className="font-medium text-gray-900">{incident.category}</p>
              <span
                className={`rounded-full px-2 py-0.5 text-xs font-medium ${
                  SEVERITY_COLOR[incident.severity] ?? 'bg-gray-100 text-gray-700'
                }`}
              >
                {incident.severity}
              </span>
            </div>
            <p className="mt-1 text-sm text-gray-500">
              {incident.affectedService} · {incident.affectedEndpoint} · {incident.occurrenceCount} occurrences
            </p>
            <p className="mt-2 text-sm text-gray-700">{incident.representativeError}</p>
            {incident.probableCause && (
              <p className="mt-2 text-sm text-gray-600">
                <span className="font-medium">Probable cause:</span> {incident.probableCause}
              </p>
            )}
            {incident.recommendation && (
              <p className="mt-1 text-sm text-gray-600">
                <span className="font-medium">Recommendation:</span> {incident.recommendation}
              </p>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}
