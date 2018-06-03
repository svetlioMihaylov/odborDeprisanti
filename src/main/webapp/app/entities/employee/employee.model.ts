import { BaseEntity } from './../../shared';

export const enum Position {
    'NEW_JOINER',
    'ACTIVE',
    'LEFT'
}

export const enum Sex {
    'MALE',
    'FEMALE',
    'OTHER',
    'UNICORN',
    'APACHE_ATTACK_HELICOPTER'
}

export class Employee implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public middleName?: string,
        public lastName?: string,
        public firstNameNative?: string,
        public middleNameNative?: string,
        public lastNameNative?: string,
        public citizenship?: string,
        public dateOfBirth?: any,
        public education?: string,
        public educationNative?: string,
        public position?: Position,
        public sex?: Sex,
        public tShirtSiz?: string,
        public contactInformation?: BaseEntity,
        public employeePhoto?: BaseEntity,
        public workDetails?: BaseEntity,
        public emargencyContact?: BaseEntity,
        public idcard?: BaseEntity,
        public financialDetails?: BaseEntity,
        public benefits?: BaseEntity[],
        public documents?: BaseEntity[],
        public externalPeople?: BaseEntity[],
        public notes?: BaseEntity[],
        public vacationRequests?: BaseEntity[],
    ) {
    }
}
