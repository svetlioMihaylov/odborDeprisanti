import { BaseEntity } from './../../shared';

export class FinancialDetails implements BaseEntity {
    constructor(
        public id?: number,
        public iban?: string,
        public bankName?: string,
        public bic?: string,
        public baseSalary?: number,
        public grossSalary?: number,
        public signOnBonus?: number,
        public annualBonus?: number,
    ) {
    }
}
