import axios from 'axios';

const API_BASE = 'http://localhost:8080/api/employees';

export const employeeApi = {
  list: (params) => axios.get(API_BASE, { params }).then((res) => res.data),
  getById: (id) => axios.get(`${API_BASE}/${id}`).then((res) => res.data),
  create: (payload) => axios.post(API_BASE, payload).then((res) => res.data),
  update: (id, payload) => axios.put(`${API_BASE}/${id}`, payload).then((res) => res.data),
  remove: (id) => axios.delete(`${API_BASE}/${id}`),
};
