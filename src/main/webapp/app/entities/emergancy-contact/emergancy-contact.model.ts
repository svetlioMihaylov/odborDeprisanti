import { BaseEntity } from './../../shared';

export class EmergancyContact implements BaseEntity {
    constructor(
        public id?: number,
        public emergencyContactName?: string,
        public emergencyContactPhone?: string,
    ) {
    }
}
