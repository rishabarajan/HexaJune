export class Asset {
  constructor(
    public asset_id: number,
    public asset_name: string,
    public asset_category: string,
    public asset_model: string,
    public manufacturing_date: string,
    public expiry_date: string,
    public asset_value: number,
    public status: string,
    public description: string,
    public image_url?: string
  ) {}
}