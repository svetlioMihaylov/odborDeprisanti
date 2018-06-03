import { BaseEntity } from './../../shared';

export class EmployeePossition implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public positionName?: string,
        public positionNameNative?: string,
    ) {
    }
}
