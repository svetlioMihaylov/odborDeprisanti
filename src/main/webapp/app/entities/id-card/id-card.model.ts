import { BaseEntity } from './../../shared';

export class IDCard implements BaseEntity {
    constructor(
        public id?: number,
        public idNumber?: string,
        public dateOfIssue?: any,
        public dateOfExpiry?: any,
        public issuedBy?: string,
    ) {
    }
}
