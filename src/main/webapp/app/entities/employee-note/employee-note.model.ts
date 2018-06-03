import { BaseEntity } from './../../shared';

export const enum EmployeeNoteType {
    'START_WORK',
    'END_WORK',
    'END_PROBATION',
    'POSSITION_CHANGE',
    'SALARY_CHANGE',
    'CUSTOM'
}

export class EmployeeNote implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public text?: string,
        public date?: any,
        public employeeNoteType?: EmployeeNoteType,
        public owner?: BaseEntity,
    ) {
    }
}
