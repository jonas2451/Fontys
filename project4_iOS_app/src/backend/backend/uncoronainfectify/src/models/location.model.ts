import {Entity, model, property} from '@loopback/repository';

@model()
export class Location extends Entity {
  @property({
    type: 'number',
    id: true,
    generated: true,
  })
  id?: number;

  @property({
    type: Number,
    datatype: 'decimal',
    precision: 9,
    scale: 6,
    required: true,
  })
  longitude: number;

  @property({
    type: Number,
    datatype: 'decimal',
    precision: 9,
    scale: 6,
    required: true,
  })
  latitude: number;


  constructor(data?: Partial<Location>) {
    super(data);
  }
}

export interface LocationRelations {
  // describe navigational properties here
}

export type LocationWithRelations = Location & LocationRelations;
