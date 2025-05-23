import { Injectable } from '@nestjs/common';

@Injectable()
export class AppService {

  // Variável declarada mas nunca usada (code smell)
  private readonly unusedVariable: string = 'I am never used';

  getHello(): string {
    const unreachableCode = true;

    // Código morto: este return faz com que o próximo console.log nunca seja executado
    return 'Hello World!';

    // Código morto: nunca será executado
    console.log('This is unreachable code');
  }
}
