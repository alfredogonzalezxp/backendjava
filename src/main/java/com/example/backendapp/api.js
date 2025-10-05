const API_URL = import.meta.env.VITE_API_URL;

export const apiClient = {
    getPlans: () => fetch(`${API_URL}/plans`).then(res => res.json()),

    createPlan: (planData) => fetch(`${API_URL}/plans`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(planData),
    }).then(res => res.json()),

    getClients: () => fetch(`${API_URL}/clients`).then(res => res.json()),

    getClientById: (id) => fetch(`${API_URL}/clients/${id}`).then(res => res.json()),

    createClient: (clientData) => fetch(`${API_URL}/clients`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(clientData),
    }).then(res => res.json()),

    getSubscriptions: () => fetch(`${API_URL}/subscriptions`).then(res => res.json()),

    createSubscription: (subscriptionData) => fetch(`${API_URL}/subscriptions`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(subscriptionData),
    }).then(res => res.json()),

    getInvoices: () => fetch(`${API_URL}/invoices`).then(res => res.json()),

    payInvoice: (invoiceId) => fetch(`${API_URL}/invoices/${invoiceId}/pay`, {
        method: 'POST',
    }).then(res => {
        if (!res.ok) throw new Error('Failed to pay invoice');
        const contentType = res.headers.get('content-type');
        if (contentType && contentType.indexOf('application/json') !== -1) {
            return res.json();
        }
        return { id: invoiceId, status: 'PAID' };
    }),
};