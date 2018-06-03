import { BaseEntity } from './../../shared';

export class Document implements BaseEntity {
    constructor(
        public id?: number,
        public fileLocation?: string,
        public createdOn?: any,
        public markedForDelete?: boolean,
        public owner?: BaseEntity,
    ) {
        this.markedForDelete = false;
    }
}
