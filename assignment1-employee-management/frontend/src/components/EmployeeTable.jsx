export default function EmployeeTable({ employees, onEdit, onDelete }) {
  if (employees.length === 0) {
    return <p className="empty-state">No employees found. Try adjusting your search or filter.</p>;
  }

  return (
    <table className="employee-table">
      <thead>
        <tr>
          <th>Name</th>
          <th>Email</th>
          <th>Department</th>
          <th>Designation</th>
          <th>Salary</th>
          <th>Joining Date</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {employees.map((emp) => (
          <tr key={emp.id}>
            <td>{emp.name}</td>
            <td>{emp.email}</td>
            <td>{emp.department}</td>
            <td>{emp.designation}</td>
            <td>${emp.salary.toLocaleString()}</td>
            <td>{emp.joiningDate}</td>
            <td className="row-actions">
              <button className="btn small" onClick={() => onEdit(emp)}>Edit</button>
              <button className="btn small danger" onClick={() => onDelete(emp)}>Delete</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
