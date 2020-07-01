import {
  Count,
  CountSchema,
  Filter,
  repository,
  Where,
} from '@loopback/repository';
import {
  post,
  param,
  get,
  getFilterSchemaFor,
  getModelSchemaRef,
  getWhereSchemaFor,
  patch,
  put,
  del,
  requestBody,
  isSchemaObject,
} from '@loopback/rest';
import {Person} from '../models';
import {PersonRepository} from '../repositories';
var apn = require('apn');
export class PersonController {
  constructor(
    @repository(PersonRepository)
    public personRepository : PersonRepository,
  ) {}

  @post('/persons', {
    responses: {
      '200': {
        description: 'Person model instance',
        content: {'application/json': {schema: getModelSchemaRef(Person)}},
      },
    },
  })
  async create(
    @requestBody({
      content: {
        'application/json': {
          schema: getModelSchemaRef(Person, {
            title: 'NewPerson',
            exclude: ['id'],
          }),
        },
      },
    })
    person: Omit<Person, 'id'>,
  ): Promise<Person> {
    return this.personRepository.create(person);
  }

  @get('/persons/count', {
    responses: {
      '200': {
        description: 'Person model count',
        content: {'application/json': {schema: CountSchema}},
      },
    },
  })
  async count(
    @param.query.object('where', getWhereSchemaFor(Person)) where?: Where<Person>,
  ): Promise<Count> {
    return this.personRepository.count(where);
  }

  @get('/persons', {
    responses: {
      '200': {
        description: 'Array of Person model instances',
        content: {
          'application/json': {
            schema: {
              type: 'array',
              items: getModelSchemaRef(Person, {includeRelations: true}),
            },
          },
        },
      },
    },
  })
  async find(
    @param.query.object('filter', getFilterSchemaFor(Person)) filter?: Filter<Person>,
  ): Promise<Person[]> {
    return this.personRepository.find(filter);
  }

  @patch('/persons', {
    responses: {
      '200': {
        description: 'Person PATCH success count',
        content: {'application/json': {schema: CountSchema}},
      },
    },
  })
  async updateAll(
    @requestBody({
      content: {
        'application/json': {
          schema: getModelSchemaRef(Person, {partial: true}),
        },
      },
    })
    person: Person,
    @param.query.object('where', getWhereSchemaFor(Person)) where?: Where<Person>,
  ): Promise<Count> {
    return this.personRepository.updateAll(person, where);
  }

  @get('/persons/{id}', {
    responses: {
      '200': {
        description: 'Person model instance',
        content: {
          'application/json': {
            schema: getModelSchemaRef(Person, {includeRelations: true}),
          },
        },
      },
    },
  })
  async findById(
    @param.path.number('id') id: number,
    @param.query.object('filter', getFilterSchemaFor(Person)) filter?: Filter<Person>
  ): Promise<Person> {
    return this.personRepository.findById(id, filter);
  }

  @patch('/persons/{id}', {
    responses: {
      '204': {
        description: 'Person PATCH success',
      },
    },
  })
  async updateById(
    @param.path.number('id') id: number,
    @requestBody({
      content: {
        'application/json': {
          schema: getModelSchemaRef(Person, {partial: true}),
        },
      },
    })
    person: Person,
  ): Promise<void> {
    if(person.healthstate == "ill"){
      this.sendPushNotification();
    }
    await this.personRepository.updateById(id, person);
  }

  @put('/persons/{id}', {
    responses: {
      '204': {
        description: 'Person PUT success',
      },
    },
  })
  async replaceById(
    @param.path.number('id') id: number,
    @requestBody() person: Person,
  ): Promise<void> {
    await this.personRepository.replaceById(id, person);
  }

  @del('/persons/{id}', {
    responses: {
      '204': {
        description: 'Person DELETE success',
      },
    },
  })
  async deleteById(@param.path.number('id') id: number): Promise<void> {
    await this.personRepository.deleteById(id);
  }

  sendPushNotification(): string {
    var options = {
      token: {
        key: "./AuthKey_MVJHJ8K8MU.p8",
        keyId: "MVJHJ8K8MU",
        teamId: "KH7BD93PMV"
      },
      production: false
    };
     
    var apnProvider = new apn.Provider(options);
    let list: Array<string> = ["de3217be8e932cb50b821824eaf05932e6724e373a9d83ec4d64245fafb76112",
    "cdbfcec096530762555982b821b3a9cb621a534fa8da4de9b66bb86021788e25", "0284d6cfeb28a4fa1bf7723f53ec1ddbd692ab4242779a32108ebd9a5d7e5408", "08fb27638c46851bb7873338d3ebe623bccb253fb6f67b4ec9c280cb80d5b79d"];
    //let deviceToken = "56c8a7ea7d9833dc6e254d75d43cecd1edb7574aedf6c8d84b7946e9149d0108";
    var note = new apn.Notification();
 
    note.expiry = Math.floor(Date.now() / 1000) + 3600; // Expires 1 hour from now.
    note.badge = 3;
    note.sound = "ping.aiff";
    note.alert = "\uD83D\uDCE7 \u2709 A person that you met, might be infected. Care!";
    note.payload = {'messageFrom': 'John Appleseed'};
    note.topic = "nl.fontys.apps20.G9UncoronaInfectify";
    //apnProvider.send(note, deviceToken).then( (result) => {
    //  // see documentation for an explanation of result
    //});
    list.forEach(function (value) {
      apnProvider.send(note, value);
    });
  
    return 'Push notification sent!';
  }
}
