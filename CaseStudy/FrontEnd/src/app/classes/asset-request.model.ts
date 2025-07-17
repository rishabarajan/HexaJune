export class AssetRequest {
  constructor(
    public requestId: number,
    public employeeId: number,
    public assetId: number,
    public requestDate: string,
    public status: string,
    public requestType: string,
    public description?: string,
    public issueType?: string,
    public employeeName?: string, // Optional display data
    public assetName?: string     // Optional display data
  ) {}
}
