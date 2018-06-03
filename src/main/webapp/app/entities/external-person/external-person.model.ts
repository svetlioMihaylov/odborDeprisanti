import { BaseEntity } from './../../shared';

export class ExternalPerson implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public middleName?: string,
        public lastName?: string,
        public firstNameNative?: string,
        public middleNameNative?: string,
        public lastNameNative?: string,
        public owners?: BaseEntity[],
        public owner?: BaseEntity,
    ) {
    }
}
