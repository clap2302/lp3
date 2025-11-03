// UNIVERSIDADE DO ESTADO DA BAHIA
// Departamento de Ciências Exatas e da Terra, Campus I
// Colegiado de Sistemas de Informação / Semestre: 2025.2
// Disciplina: Linguagem de Programação III / Professor: Vagner Fonseca
// Aluno: _____________________________________________________________
// Exercício 02
// Sistema de Estacionamento Inteligente com Controle de Acesso
// A Estacione na B Estacionamentos Ltda. administra o pátio de sua unidade central. Por
// norma interna alinhada à lei municipal de priorização (idosos, PCD, gestantes), o
// estacionamento possui 7 vagas, sendo 5 vagas NORMAIS e 2 vagas PRIORITÁRIAS
// (sinalizadas e reservadas). Auditorias periódicas exigem comprovantes de acesso (logs)
// e indicadores de recusa por falta de vagas.
// Nos horários de pico (7h–10h e 16h–19h), a chegada de veículos é intensa e imprevisível.
// Para garantir tempo de resposta e segurança de concorrência, a Estacione na B decidiu
// implementar um software de controle do estacionamento em Java, utilizando
// processamento concorrente via thread pool (Executor) e controle de capacidade via
// Semaphore.
// Você foi contratado pela empresa para implementar um sistema que gerencia um
// estacionamento com vagas limitadas e controla o fluxo de veículos entrando e saindo.
// Os veículos devem ser categorizados em NORMAL ou PRIORITARIO. O estacionamento
// deve garantir que veículos prioritários tenham preferência no alocamento das vagas.
// Além disso, o sistema deve registrar o log de entrada e saída de veículos, e um contador
// de veículos que não conseguiram estacionar por falta de vaga.
// REQUISITOS:
// 1. O estacionamento possui:
// a. 5 vagas regulares
// b. 2 vagas para idosos/deficientes (prioritárias)
// c. 1 portão de entrada (apenas 1 veículo por vez)
// d. 1 portão de saída (apenas 1 veículo por vez)
// 2. Use Semaphore para controlar:
// a. Vagas regulares disponíveis
// b. Vagas prioritárias disponíveis
// c. Acesso ao portão de entrada
// d. Acesso ao portão de saída
// 3. Use Executors para:
// a. Gerenciar chegada de veículos (FixedThreadPool com 4 threads)
// b. Gerenciar a chegada de 20 veículos. Os veículos devem ser gerados
// aleatoriamente sendo 30% prioritários e 70% normais.
// c. Simular permanência e saída dos veículos
// 4. Cada veículo deve:
// a. Esperar para entrar pelo portão (acquire portão entrada)
// b. Tentar conseguir uma vaga (prioritária primeiro, se aplicável)
// c. Permanecer estacionado por tempo aleatório (1-5 segundos)
// d. Sair pelo portão (acquire portão saída)
// e. Liberar todos os recursos ao sair
// UNIVERSIDADE DO ESTADO DA BAHIA
// Departamento de Ciências Exatas e da Terra, Campus I
// Colegiado de Sistemas de Informação / Semestre: 2025.2
// Disciplina: Linguagem de Programação III / Professor: Vagner Fonseca
// Aluno: _____________________________________________________________
// Saida esperada
// === Sistema de Estacionamento Inteligente ===
// === GERANDO VEÍCULOS ===
// Gerado: Veículo #1 (NORMAL)
// Gerado: Veículo #2 (NORMAL)
// Gerado: Veículo #3 (NORMAL)
// Gerado: Veículo #4 (NORMAL)
// Gerado: Veículo #5 (NORMAL)
// Gerado: Veículo #6 (PRIORITARIO)
// Gerado: Veículo #7 (NORMAL)
// Gerado: Veículo #8 (NORMAL)
// Gerado: Veículo #9 (NORMAL)
// Gerado: Veículo #10 (PRIORITARIO)
// Gerado: Veículo #11 (PRIORITARIO)
// Gerado: Veículo #12 (NORMAL)
// Gerado: Veículo #13 (NORMAL)
// Gerado: Veículo #14 (NORMAL)
// Gerado: Veículo #15 (PRIORITARIO)
// Gerado: Veículo #16 (NORMAL)
// Gerado: Veículo #17 (NORMAL)
// Gerado: Veículo #18 (NORMAL)
// Gerado: Veículo #19 (NORMAL)
// Gerado: Veículo #20 (PRIORITARIO)
// === INICIANDO SIMULAÇÃO ===
// Veículo #1 (NORMAL) chegou ao portão de entrada
// Veículo #1 (NORMAL) conseguiu vaga REGULAR
// Veículo #1 (NORMAL) está estacionado por 3881ms
// Veículo #2 (NORMAL) chegou ao portão de entrada
// Veículo #2 (NORMAL) conseguiu vaga REGULAR
// Veículo #2 (NORMAL) está estacionado por 4724ms
// Veículo #3 (NORMAL) chegou ao portão de entrada
// Veículo #3 (NORMAL) conseguiu vaga REGULAR
// Veículo #3 (NORMAL) está estacionado por 1459ms
// Veículo #4 (NORMAL) chegou ao portão de entrada
// Veículo #4 (NORMAL) conseguiu vaga REGULAR
// Veículo #4 (NORMAL) está estacionado por 2281ms
// Veículo #3 (NORMAL) está saindo do estacionamento...
// Veículo #3 (NORMAL) saiu com sucesso!
// Veículo #5 (NORMAL) chegou ao portão de entrada
// Veículo #5 (NORMAL) conseguiu vaga REGULAR
// Veículo #5 (NORMAL) está estacionado por 3419ms
// Veículo #4 (NORMAL) está saindo do estacionamento...
// UNIVERSIDADE DO ESTADO DA BAHIA
// Departamento de Ciências Exatas e da Terra, Campus I
// Colegiado de Sistemas de Informação / Semestre: 2025.2
// Disciplina: Linguagem de Programação III / Professor: Vagner Fonseca
// Aluno: _____________________________________________________________
// Veículo #4 (NORMAL) saiu com sucesso!
// Veículo #6 (PRIORITARIO) chegou ao portão de entrada
// Veículo #6 (PRIORITARIO) conseguiu vaga PRIORITÁRIA
// Veículo #6 (PRIORITARIO) está estacionado por 4929ms
// Veículo #1 (NORMAL) está saindo do estacionamento...
// Veículo #1 (NORMAL) saiu com sucesso!
// Veículo #7 (NORMAL) chegou ao portão de entrada
// Veículo #7 (NORMAL) conseguiu vaga REGULAR
// Veículo #7 (NORMAL) está estacionado por 2659ms
// Veículo #2 (NORMAL) está saindo do estacionamento...
// Veículo #2 (NORMAL) saiu com sucesso!
// Veículo #8 (NORMAL) chegou ao portão de entrada
// Veículo #8 (NORMAL) conseguiu vaga REGULAR
// Veículo #8 (NORMAL) está estacionado por 1400ms
// Veículo #5 (NORMAL) está saindo do estacionamento...
// Veículo #5 (NORMAL) saiu com sucesso!
// Veículo #9 (NORMAL) chegou ao portão de entrada
// Veículo #9 (NORMAL) conseguiu vaga REGULAR
// Veículo #9 (NORMAL) está estacionado por 3191ms
// Veículo #8 (NORMAL) está saindo do estacionamento...
// Veículo #8 (NORMAL) saiu com sucesso!
// Veículo #10 (PRIORITARIO) chegou ao portão de entrada
// Veículo #10 (PRIORITARIO) conseguiu vaga PRIORITÁRIA
// Veículo #10 (PRIORITARIO) está estacionado por 2618ms
// Veículo #7 (NORMAL) está saindo do estacionamento...
// Veículo #7 (NORMAL) saiu com sucesso!
// Veículo #11 (PRIORITARIO) chegou ao portão de entrada
// Veículo #6 (PRIORITARIO) está saindo do estacionamento...
// Veículo #6 (PRIORITARIO) saiu com sucesso!
// Veículo #11 (PRIORITARIO) conseguiu vaga PRIORITÁRIA
// Veículo #11 (PRIORITARIO) está estacionado por 4071ms
// Veículo #12 (NORMAL) chegou ao portão de entrada
// Veículo #12 (NORMAL) conseguiu vaga REGULAR
// Veículo #12 (NORMAL) está estacionado por 4186ms
// Veículo #9 (NORMAL) está saindo do estacionamento...
// Veículo #9 (NORMAL) saiu com sucesso!
// Veículo #13 (NORMAL) chegou ao portão de entrada
// Veículo #13 (NORMAL) conseguiu vaga REGULAR
// Veículo #13 (NORMAL) está estacionado por 3515ms
// Veículo #10 (PRIORITARIO) está saindo do estacionamento...
// Veículo #10 (PRIORITARIO) saiu com sucesso!
// Veículo #14 (NORMAL) chegou ao portão de entrada
// Veículo #14 (NORMAL) conseguiu vaga REGULAR
// Veículo #14 (NORMAL) está estacionado por 2434ms
// Veículo #14 (NORMAL) está saindo do estacionamento...
// UNIVERSIDADE DO ESTADO DA BAHIA
// Departamento de Ciências Exatas e da Terra, Campus I
// Colegiado de Sistemas de Informação / Semestre: 2025.2
// Disciplina: Linguagem de Programação III / Professor: Vagner Fonseca
// Aluno: _____________________________________________________________
// Veículo #14 (NORMAL) saiu com sucesso!
// Veículo #15 (PRIORITARIO) chegou ao portão de entrada
// Veículo #15 (PRIORITARIO) conseguiu vaga PRIORITÁRIA
// Veículo #15 (PRIORITARIO) está estacionado por 4548ms
// Veículo #11 (PRIORITARIO) está saindo do estacionamento...
// Veículo #11 (PRIORITARIO) saiu com sucesso!
// Veículo #16 (NORMAL) chegou ao portão de entrada
// Veículo #16 (NORMAL) conseguiu vaga REGULAR
// Veículo #16 (NORMAL) está estacionado por 2330ms
// Veículo #12 (NORMAL) está saindo do estacionamento...
// Veículo #12 (NORMAL) saiu com sucesso!
// Veículo #17 (NORMAL) chegou ao portão de entrada
// Veículo #17 (NORMAL) conseguiu vaga REGULAR
// Veículo #17 (NORMAL) está estacionado por 1621ms
// Veículo #13 (NORMAL) está saindo do estacionamento...
// Veículo #13 (NORMAL) saiu com sucesso!
// Veículo #18 (NORMAL) chegou ao portão de entrada
// Veículo #18 (NORMAL) conseguiu vaga REGULAR
// Veículo #18 (NORMAL) está estacionado por 3919ms
// Veículo #17 (NORMAL) está saindo do estacionamento...
// Veículo #17 (NORMAL) saiu com sucesso!
// Veículo #19 (NORMAL) chegou ao portão de entrada
// Veículo #19 (NORMAL) conseguiu vaga REGULAR
// Veículo #19 (NORMAL) está estacionado por 3754ms
// Veículo #16 (NORMAL) está saindo do estacionamento...
// Veículo #16 (NORMAL) saiu com sucesso!
// Veículo #20 (PRIORITARIO) chegou ao portão de entrada
// Veículo #20 (PRIORITARIO) conseguiu vaga PRIORITÁRIA
// Veículo #20 (PRIORITARIO) está estacionado por 1526ms
// Veículo #20 (PRIORITARIO) está saindo do estacionamento...
// Veículo #20 (PRIORITARIO) saiu com sucesso!
// Veículo #18 (NORMAL) está saindo do estacionamento...
// Veículo #18 (NORMAL) saiu com sucesso!
// Veículo #15 (PRIORITARIO) está saindo do estacionamento...
// Veículo #15 (PRIORITARIO) saiu com sucesso!
// Veículo #19 (NORMAL) está saindo do estacionamento...
// Veículo #19 (NORMAL) saiu com sucesso!
// === STATUS DO ESTACIONAMENTO ===
// Vagas regulares disponíveis: 5/5
// Vagas prioritárias disponíveis: 2/2
// Total de entradas: 20
// Total de desistências: 0
// ================================
// UNIVERSIDADE DO ESTADO DA BAHIA
// Departamento de Ciências Exatas e da Terra, Campus I
// Colegiado de Sistemas de Informação / Semestre: 2025.2
// Disciplina: Linguagem de Programação III / Professor: Vagner Fonseca
// Aluno: _____________________________________________________________
// === ESTATÍSTICAS FINAIS ===
// Total de veículos: 20
// Conseguiram entrar: 20
// Desistiram: 0
// Taxa de sucesso: 100.0%
// Tempo médio de permanência: 3177.45 ms (3.18 segundos)
// Tempo mínimo: 1407 ms (1.41 segundos)
// Tempo máximo: 5051 ms (5.05 segundos)
// Taxa de ocupação: 100.0%
// ===========================
// === Simulação Concluída ===

package task2_Leandro;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Main
{
    public static void main(String[] args)
    {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int i = 1; i <= 20; i++)
        {
            vehicles.add(new Vehicle(i));
            System.out.println("Gerado: Veículo #" + i + " (" + vehicles.get(i - 1).getType() + ")");
        }

        System.out.println("=== INICIANDO SIMULAÇÃO ===");

        for (Vehicle vehicle : vehicles)
        {
            executor.submit(new VehicleTask(vehicle, parkingLot));
        }
    }
}

enum Type { NORMAL, PRIORITY }

class Vehicle
{
    private final int id;
    private final Type type;

    public Vehicle(int id)
    {
        this.id = id;
        this.type = new Random().nextInt(1, 10) <= 7 ? Type.NORMAL : Type.PRIORITY;
    }

    public int getId()
    {
        return id;
    }
    public Type getType()
    {
        return type;
    }
}

class ParkingLot
{
    Semaphore regularSpots = new Semaphore(5);
    Semaphore handicappedSpots = new Semaphore(2);
    Semaphore entryGate = new Semaphore(1);
    Semaphore exitGate = new Semaphore(1);

    public void enterRegular() throws InterruptedException
    {
        entryGate.acquire();
        regularSpots.acquire();
        entryGate.release();
    }

    public void enterHandicapped() throws InterruptedException
    {
        entryGate.acquire();
        handicappedSpots.acquire();
        entryGate.release();
    }

    public void exitRegular() throws InterruptedException
    {
        exitGate.acquire();
        regularSpots.release();
        exitGate.release();
    }

    public void exitHandicapped() throws InterruptedException
    {
        exitGate.acquire();
        handicappedSpots.release();
        exitGate.release();
    }
}

class VehicleTask implements Runnable
{
    private final Vehicle vehicle;
    private final ParkingLot parkingLot;

    public VehicleTask(Vehicle vehicle, ParkingLot parkingLot)
    {
        this.vehicle = vehicle;
        this.parkingLot = parkingLot;
    }

    @Override
    public void run()
    {
        try
        {
            System.out.println("Veículo #" + vehicle.getId() + " (" + vehicle.getType() + ") chegou ao portão de entrada");
            if (vehicle.getType() == Type.PRIORITY)
            {
                parkingLot.enterHandicapped();
                System.out.println("Veículo #" + vehicle.getId() + " conseguiu vaga PRIORITÁRIA");
            }
            else
            {
                parkingLot.enterRegular();
                System.out.println("Veículo #" + vehicle.getId() + " conseguiu vaga REGULAR");
            }

            int duration = new Random().nextInt(1000, 20000);
            Thread.sleep(duration);

            System.out.println("Veículo #" + vehicle.getId() + " está estacionado por " + duration + "ms");

            System.out.println("Veículo #" + vehicle.getId() + " está saindo do estacionamento...");
            if (vehicle.getType() == Type.PRIORITY)
            {
                parkingLot.exitHandicapped();
            }
            else
            {
                parkingLot.exitRegular();
            }

            System.out.println("Veículo #" + vehicle.getId() + " saiu com sucesso!");

        } catch (InterruptedException e)
        {
            System.out.println("Veículo #" + vehicle.getId() + " interrompido.");
        }
    }
}
