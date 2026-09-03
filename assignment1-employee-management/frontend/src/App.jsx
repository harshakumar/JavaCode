import { useEffect, useState, useCallback } from 'react';
import { employeeApi } from './api/employeeApi';
import EmployeeForm from './components/EmployeeForm';
import EmployeeTable from './components/EmployeeTable';
import './index.css';

export default function App() {
  const [employees, setEmployees] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [search, setSearch] = useState('');
  const [department, setDepartment] = useState('');
  const [editingEmployee, setEditingEmployee] = useState(null);
  const [serverErrors, setServerErrors] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const loadEmployees = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await employeeApi.list({
        page,
        size: 8,
        search: search || undefined,
        department: department || undefined,
      });
      setEmployees(data.content);
      setTotalPages(data.totalPages);
    } catch (err) {
      setError('Could not reach the backend. Is the Spring Boot server running on port 8080?');
    } finally {
      setLoading(false);
    }
  }, [page, search, department]);

  useEffect(() => {
    loadEmployees();
  }, [loadEmployees]);

  const handleSubmit = async (formData) => {
    setServerErrors(null);
    try {
      if (editingEmployee) {
        await employeeApi.update(editingEmployee.id, formData);
      } else {
        await employeeApi.create(formData);
      }
      setEditingEmployee(null);
      await loadEmployees();
    } catch (err) {
      if (err.response?.data?.fieldErrors) {
        setServerErrors(err.response.data.fieldErrors);
      } else if (err.response?.data?.message) {
        setError(err.response.data.message);
      }
    }
  };

  const handleDelete = async (employee) => {
    if (!window.confirm(`Delete ${employee.name}?`)) return;
    await employeeApi.remove(employee.id);
    await loadEmployees();
  };

  return (
    <div className="app-shell">
      <header className="app-header">
        <h1>Employee Management</h1>
        <p>A small CRUD assignment — Spring Boot REST API + React frontend.</p>
      </header>

      {error && <div className="banner error">{error}</div>}

      <section className="panel">
        <EmployeeForm
          editingEmployee={editingEmployee}
          onSubmit={handleSubmit}
          onCancel={() => setEditingEmployee(null)}
          serverErrors={serverErrors}
        />
      </section>

      <section className="panel">
        <div className="toolbar">
          <input
            className="search-input"
            placeholder="Search by name..."
            value={search}
            onChange={(e) => { setSearch(e.target.value); setPage(0); }}
          />
          <input
            className="filter-input"
            placeholder="Filter by department..."
            value={department}
            onChange={(e) => { setDepartment(e.target.value); setPage(0); }}
          />
        </div>

        {loading ? (
          <p>Loading...</p>
        ) : (
          <EmployeeTable
            employees={employees}
            onEdit={setEditingEmployee}
            onDelete={handleDelete}
          />
        )}

        <div className="pagination">
          <button disabled={page === 0} onClick={() => setPage((p) => p - 1)}>Previous</button>
          <span>Page {page + 1} of {Math.max(totalPages, 1)}</span>
          <button disabled={page + 1 >= totalPages} onClick={() => setPage((p) => p + 1)}>Next</button>
        </div>
      </section>
    </div>
  );
}
