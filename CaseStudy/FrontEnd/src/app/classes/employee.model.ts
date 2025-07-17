
export class Employee {
  constructor(
    public employee_id: number,
    public employee_name: string,
    public user_name: string,
    public password: string,
    public email: string,
    public contact_number: string,
    public address: string,
    public role: string,
    public join_date: Date,
    public gender: string
  ) {}
}