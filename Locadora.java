// Interface para representar uma Fita de vídeo
interface Fita {
  String getTítulo();
  int getCódigoDePreço();
}

// Implementação da interface Fita
class FitaImpl implements Fita {
  private String título;
  private int códigoDePreço;

  public FitaImpl(String título, int códigoDePreço) {
    this.título = título;
    this.códigoDePreço = códigoDePreço;
  }

  @Override
  public String getTítulo() {
    return título;
  }

  @Override
  public int getCódigoDePreço() {
    return códigoDePreço;
  }
}

// Interface para representar um Aluguel
interface Aluguel {
  Fita getFita();
  int getDiasAlugada();
  double calcularPreço();
  int calcularPontosDeAlugadorFrequente();
}

// Implementação da interface Aluguel
class AluguelImpl implements Aluguel {
  private Fita fita;
  private int diasAlugada;

  public AluguelImpl(Fita fita, int diasAlugada) {
    this.fita = fita;
    this.diasAlugada = diasAlugada;
  }

  @Override
  public Fita getFita() {
    return fita;
  }

  @Override
  public int getDiasAlugada() {
    return diasAlugada;
  }

  @Override
  public double calcularPreço() {
    double valorCorrente = 0.0;
    switch (fita.getCódigoDePreço()) {
        case Fita.NORMAL:
            valorCorrente += 2;
            if (diasAlugada > 2) {
                valorCorrente += (diasAlugada - 2) * 1.5;
            }
            break;
        case Fita.LANÇAMENTO:
            valorCorrente += diasAlugada * 3;
            break;
        case Fita.INFANTIL:
            valorCorrente += 1.5;
            if (diasAlugada > 3) {
                valorCorrente += (diasAlugada - 3) * 1.5;
            }
            break;
      }
      return valorCorrente;
  }

  @Override
  public int calcularPontosDeAlugadorFrequente() {
    int pontosDeAlugadorFrequente = 1;
    if (fita.getCódigoDePreço() == Fita.LANÇAMENTO && diasAlugada > 1) {
        pontosDeAlugadorFrequente++;
      }
      return pontosDeAlugadorFrequente;
  }
}

// Interface para representar um Cliente
interface Cliente {
  String getNome();
  void adicionaAluguel(Aluguel aluguel);
  String extrato();
}

// Implementação da interface Cliente
class ClienteImpl implements Cliente {
  private String nome;
  private List<Aluguel> alugueis = new ArrayList<>();

  public ClienteImpl(String nome) {
    this.nome = nome;
  }

  @Override
  public String getNome() {
    return nome;
  }

  @Override
  public void adicionaAluguel(Aluguel aluguel) {
    alugueis.add(aluguel);
  }

    @Override
    public String extrato() {
        StringBuilder resultado = new StringBuilder("Registro de Alugueis de ").append(nome).append(System.getProperty("line.separator"));
        double valorTotal = 0.0;
        int pontosDeAlugadorFrequente = 0;

        for (Aluguel aluguel : alugueis) {
            double valorCorrente = aluguel.calcularPreço();
            pontosDeAlugadorFrequente += aluguel.calcularPontosDeAlugadorFrequente();

            resultado.append("\t").append(aluguel.getFita().getTítulo()).append("\t").append(valorCorrente).append(System.getProperty("line.separator"));
            valorTotal += valorCorrente;
        }

        resultado.append("Valor total devido: ").append(valorTotal).append(System.getProperty("line.separator"));
        resultado.append("Você acumulou ").append(pontosDeAlugadorFrequente).append(" pontos de alugador frequente");

        return resultado.toString();
    }
}

public class Locadora {
  public static void main(String[] args) {
    Cliente cliente = new ClienteImpl("Juliana");
    cliente.adicionaAluguel(new AluguelImpl(new FitaImpl("O Exorcista ", Fita.NORMAL), 3));
    cliente.adicionaAluguel(new AluguelImpl(new FitaImpl("Men in Black ", Fita.NORMAL), 2));
    cliente.adicionaAluguel(new AluguelImpl(new FitaImpl("Jurassic Park III ", Fita.LANÇAMENTO), 3));
    cliente.adicionaAluguel(new AluguelImpl(new FitaImpl("Planeta dos Macacos ", Fita.LANÇAMENTO), 4));
    cliente.adicionaAluguel(new AluguelImpl(new FitaImpl("Pateta no Planeta dos Macacos ", Fita.INFANTIL), 10));
    cliente.adicionaAluguel(new AluguelImpl(new FitaImpl("O Rei Leao ", Fita.INFANTIL), 30));

    System.out.println(cliente.extrato());
  }
}
