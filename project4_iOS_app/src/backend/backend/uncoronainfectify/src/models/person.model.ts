import {Entity, model, property} from '@loopback/repository';

@model()
export class Person extends Entity {
  @property({
    type: 'number',
    id: true,
    generated: true,
  })
  id?: number;

  @property({
    type: 'string',
    required: true,
  })
  phonenumber: string;

  @property({
    type: 'string',
  })
  healthstate?: string;


  constructor(data?: Partial<Person>) {
    super(data);
  }
}

export interface PersonRelations {
  // describe navigational properties here
}

export type PersonWithRelations = Person & PersonRelations;
