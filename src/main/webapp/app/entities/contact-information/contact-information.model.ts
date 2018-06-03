import { BaseEntity } from './../../shared';

export class ContactInformation implements BaseEntity {
    constructor(
        public id?: number,
        public permanentAddress?: string,
        public currentAddress?: string,
        public personalMail?: string,
        public phone?: string,
    ) {
    }
}
