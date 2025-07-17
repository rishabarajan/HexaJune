export class Admin {
  constructor(
    public admin_id: number,
    public admin_name: string,
    public user_name: string,
    public password: string,
    public email: string,
    public contact_number: string,
    public role: string,
    public gender: string,
    public created_at: Date
  ) {}
}