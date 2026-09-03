import { useEffect, useState } from 'react';

const emptyForm = {
  name: '',
  email: '',
  department: '',
  designation: '',
  salary: '',
  joiningDate: '',
};

export default function EmployeeForm({ editingEmployee, onSubmit, onCancel, serverErrors }) {
  const [form, setForm] = useState(emptyForm);

  useEffect(() => {
    if (editingEmployee) {
      setForm({
        name: editingEmployee.name,
        email: editingEmployee.email,
        department: editingEmployee.department,
        designation: editingEmployee.designation,
        salary: editingEmployee.salary,
        joiningDate: editingEmployee.joiningDate,
      });
    } else {
      setForm(emptyForm);
    }
  }, [editingEmployee]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({ ...form, salary: parseFloat(form.salary) });
  };

  return (
    <form className="employee-form" onSubmit={handleSubmit}>
      <h2>{editingEmployee ? 'Edit Employee' : 'Add Employee'}</h2>

      <div className="form-grid">
        <label>
          Name
          <input name="name" value={form.name} onChange={handleChange} required />
          {serverErrors?.name && <span className="field-error">{serverErrors.name}</span>}
        </label>

        <label>
          Email
          <input type="email" name="email" value={form.email} onChange={handleChange} required />
          {serverErrors?.email && <span className="field-error">{serverErrors.email}</span>}
        </label>

        <label>
          Department
          <input name="department" value={form.department} onChange={handleChange} required />
        </label>

        <label>
          Designation
          <input name="designation" value={form.designation} onChange={handleChange} required />
        </label>

        <label>
          Salary
          <input type="number" name="salary" value={form.salary} onChange={handleChange} required min="0" step="1000" />
          {serverErrors?.salary && <span className="field-error">{serverErrors.salary}</span>}
        </label>

        <label>
          Joining Date
          <input type="date" name="joiningDate" value={form.joiningDate} onChange={handleChange} required />
        </label>
      </div>

      <div className="form-actions">
        <button type="submit" className="btn primary">
          {editingEmployee ? 'Save Changes' : 'Add Employee'}
        </button>
        {editingEmployee && (
          <button type="button" className="btn ghost" onClick={onCancel}>
            Cancel
          </button>
        )}
      </div>
    </form>
  );
}
