import { BaseEntity } from './../../shared';

export const enum BenefitType {
    'PARKING',
    'PUBLIC_TRNASPORT',
    'HEALTH_INSURANCE',
    'MULTISPORT',
    'JUNK',
    'FOOL_VOUCHERS'
}

export class Benefit implements BaseEntity {
    constructor(
        public id?: number,
        public startDate?: any,
        public endDate?: any,
        public benefitType?: BenefitType,
        public employeeOwner?: BaseEntity,
        public externalPersonOwner?: BaseEntity,
        public employee?: BaseEntity,
        public benefits?: BaseEntity,
    ) {
    }
}
