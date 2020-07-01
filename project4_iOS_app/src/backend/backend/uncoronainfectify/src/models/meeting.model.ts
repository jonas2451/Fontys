import {Entity, model, property} from '@loopback/repository';

@model()
export class Meeting extends Entity {
  @property({
    type: 'number',
    id: true,
    generated: true,
  })
  id?: number;

  @property({
    type: 'number',
    required: true,
  })
  p1Id: number;

  @property({
    type: 'number',
    required: true,
  })
  p2Id: number;

  @property({
    type: 'number',
    required: true,
  })
  locationId: number;

  @property({
    type: 'date',
  })
  date?: string;


  constructor(data?: Partial<Meeting>) {
    super(data);
  }
}

export interface MeetingRelations {
  // describe navigational properties here
}

export type MeetingWithRelations = Meeting & MeetingRelations;
