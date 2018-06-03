import { BaseEntity } from './../../shared';

export class WorkDetails implements BaseEntity {
    constructor(
        public id?: number,
        public startDate?: any,
        public endDate?: any,
        public endOfProbationDate?: any,
        public salaryReevaluationDate?: number,
        public contractNum?: number,
        public resignationRequestRefNum?: number,
        public resignationOrderRefNum?: number,
        public yearVacation?: number,
        public possition?: BaseEntity,
    ) {
    }
}
