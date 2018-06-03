import { BaseEntity } from './../../shared';

export class EmployeePhoto implements BaseEntity {
    constructor(
        public id?: number,
        public photoContentType?: string,
        public photo?: any,
    ) {
    }
}
