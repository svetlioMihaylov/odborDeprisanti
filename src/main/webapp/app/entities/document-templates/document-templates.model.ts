import { BaseEntity } from './../../shared';

export class DocumentTemplates implements BaseEntity {
    constructor(
        public id?: number,
        public templateType?: string,
        public fileLocation?: string,
        public content?: any,
    ) {
    }
}
