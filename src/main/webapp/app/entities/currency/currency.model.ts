export interface ICurrency {
  id?: number;
  name?: string;
  code?: string;
  numCode?: string;
  preferred?: boolean;
}

export class Currency implements ICurrency {
  constructor(public id?: number, public name?: string, public code?: string, public numCode?: string, public preferred?: boolean) {
    this.preferred = this.preferred ?? false;
  }
}

export function getCurrencyIdentifier(currency: ICurrency): number | undefined {
  return currency.id;
}
