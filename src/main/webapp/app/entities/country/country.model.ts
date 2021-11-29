export interface ICountry {
  id?: number;
  name?: string;
  code?: string;
  codeA2?: string;
  codeA3?: string;
  flag?: string | null;
  active?: boolean;
}

export class Country implements ICountry {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string,
    public codeA2?: string,
    public codeA3?: string,
    public flag?: string | null,
    public active?: boolean
  ) {
    this.active = this.active ?? false;
  }
}

export function getCountryIdentifier(country: ICountry): number | undefined {
  return country.id;
}
