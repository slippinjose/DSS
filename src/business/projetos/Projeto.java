package business.projetos;

import business.familias.CandidaturaFactory;
import business.familias.ICandidatura;
import java.util.*;

/**
 * Classe que representa um Projeto.
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 28.12.2014
 */
class Projeto implements IProjeto{
    private int nr;
    private float orcamento;
    private float custoFinal;
    private float prestacao; 
    private GregorianCalendar dataInicial;
    private GregorianCalendar dataFinal;
    private String obs;    
    private String estado;
    private int funcionarioEncerrou;    
    private int funcionarioRegistou; 
    // private TarefaDAO tarefas; 
    private Set<Integer> tarefa;
    // Referências de voluntários que trabalha no projeto
    private Set<Integer> voluntarios; 
    // Referências da donativos feitos para o projeto -- MUDAR DIAGRAMA!! NÂO ESTÁ DEFINIDO
    private List<Integer> nrecibos; /* Numeros dos recibos de donativos deste projeto */  
    // Referência para a candidatura
    private ICandidatura candidatura;

    /**
     * Construtor vazio.
     */
    public Projeto() {
        this.nr = 0; this.estado = ""; this.obs = ""; this.orcamento = 0; this.custoFinal = 0;
        this.prestacao = 0; this.dataInicial = new GregorianCalendar(); this.dataFinal = new GregorianCalendar();
        this.funcionarioRegistou = 0; this.funcionarioEncerrou = 0; this.tarefa = new TreeSet<>();
        this.voluntarios = new TreeSet<>(); this.nrecibos = new ArrayList<>();
        this.candidatura = new CandidaturaFactory().createCandidatura();
    }
    
    /**
     * Construtor parameterizado.
     * @param nr, identificador de um projeto
     * @param estado, estado do projeto
     * @param obs, observações
     * @param orcamento, Orçamento inicial
     * @param custoFinal, custo final a pagar pela família
     * @param prestacao, prestção mensal a pagar pela família
     * @param dataInicial, data em que o projeto se iniciou
     * @param dataFinal, data em que o projeto acabou
     * @param funcionarioRegistou, funcionário que registou o projeto
     * @param funcionarioEncerrou, funcionário que encerrou o projeto
     * @param tarefa, tarefas definidas no projeto
     * @param voluntarios, voluntários que participaram no projeto
     * @param ids, identificação das equipas que participaram no projeto
     * @param nrecibos, donativos realizados para o projeto
     * @param candidatura, candidatura que originou o projeto
     */
    public Projeto(int nr, String estado, String obs, int orcamento, float custoFinal,
            float prestacao, GregorianCalendar dataInicial, GregorianCalendar dataFinal,
            int funcionarioRegistou, int funcionarioEncerrou, Set<Integer> tarefa,
            Set<Integer> voluntarios, List<Integer> ids, List<Integer> nrecibos,
            ICandidatura candidatura) {
        this.nr = nr;
        this.estado = estado;
        this.obs = obs;
        this.orcamento = orcamento;
        this.custoFinal = custoFinal;
        this.prestacao = prestacao;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.funcionarioRegistou = funcionarioRegistou;
        this.funcionarioEncerrou = funcionarioEncerrou;
        this.tarefa = tarefa;
        this.voluntarios = voluntarios;
        this.nrecibos = nrecibos;
        this.candidatura = candidatura;
    }
    
    /**
     * Construtor de cópia.
     * @param p, um Projeto
     */
    public Projeto(Projeto p) {
        this.nr = p.getNr(); this.estado = p.getEstado(); this.obs = p.getObs(); this.orcamento = p.getOrcamento();
        this.custoFinal = p.getCustoFinal(); this.prestacao = p.getPrestacao(); 
        this.dataInicial = p.getDataInicial(); this.dataFinal = p.getDataFinal();
        this.funcionarioRegistou = p.getFuncionarioReg(); this.funcionarioEncerrou = p.getFuncionarioEnc();
        this.tarefa = p.getTarefa(); this.voluntarios = p.getVoluntarios(); 
        this.nrecibos = p.getNRecibo(); this.candidatura =  p.getCandidatura();
    }

    /* Gets & Sets */
    @Override
    public int getNr() { return nr; }
    @Override
    public void setNr(int nr) { this.nr = nr; }
    @Override
    public String getEstado() { return estado; }
    @Override
    public void setEstado(String estado) { this.estado = estado; }
    @Override
    public String getObs() { return this.obs; }
    @Override
    public void setObs(String obs) { this.obs = obs; }
    @Override
    public float getOrcamento() { return orcamento; }
    @Override
    public void setOrcamento(float orcamento) { this.orcamento = orcamento; }
    @Override
    public float getCustoFinal() { return custoFinal; }
    @Override
    public void setCustoFinal(float custoFinal) { this.custoFinal = custoFinal; }
    @Override
    public float getPrestacao() { return prestacao; }
    @Override
    public void setPrestacao(float prestacao) { this.prestacao = prestacao; }
    @Override
    public GregorianCalendar getDataInicial() { return dataInicial; }
    @Override
    public void setDataInicial(GregorianCalendar dataInicial) { this.dataInicial = dataInicial; }
    @Override
    public GregorianCalendar getDataFinal() { return dataFinal; }
    @Override
    public void setDataFinal(GregorianCalendar dataFinal) { this.dataFinal = dataFinal; }
    @Override
    public int getFuncionarioReg() { return funcionarioRegistou; }
    @Override
    public void setFuncionarioReg(int fr) { this.funcionarioRegistou = fr; }
    @Override
    public int getFuncionarioEnc() { return funcionarioEncerrou; }
    @Override
    public void setFuncionarioEnc(int fr) { this.funcionarioEncerrou = fr; }
    @Override
    public Set<Integer> getTarefa() { return tarefa;}
    @Override
    public void setTarefa(Set<Integer> t) {this.tarefa = t; }
    @Override
    public Set<Integer> getVoluntarios() { return voluntarios; }
    @Override
    public void setVoluntarios(Set<Integer> voluntarios) { this.voluntarios = voluntarios; }
    @Override
    public ICandidatura getCandidatura() { return candidatura; }
    @Override
    public void setCandidatura(ICandidatura candidatura) { this.candidatura = candidatura; }
    @Override
    public List<Integer> getNRecibo(){return nrecibos;}
    @Override
    public void setNRecibo(List<Integer> e){this.nrecibos = e;}

    /* Equals e Clone */
    @Override
    public boolean equals (Object o){
        if(this==o) return true;
        else if(o==null || this.getClass()!=o.getClass()) return false;
        else{
            Projeto t = (Projeto) o;
            
            for(int f : this.tarefa){
                if(!t.getTarefa().contains(f)) return false;
            }
            for(int v : this.voluntarios){
                if(!t.getVoluntarios().contains(v)) return false;
            }
            for(int n : this.nrecibos){
                if(!t.getNRecibo().contains(n)) return false;
            }
            
            return( this.nr == t.getNr() && this.estado.equals(t.getEstado()) && this.obs.equals(t.getObs())
                    && this.orcamento == t.getOrcamento() && this.custoFinal == t.getCustoFinal()
                    && this.prestacao == t.getPrestacao() && this.dataInicial.equals(t.getDataInicial())
                    && this.dataFinal.equals(t.getDataFinal()) && this.candidatura.equals(t.getCandidatura())
                    && this.funcionarioRegistou == t.getFuncionarioReg()
                    && this.funcionarioEncerrou == t.getFuncionarioEnc());
        }
    }
        
    @Override
    public IProjeto clone(){
        return new Projeto(this);
    }
    
    @Override
    public int hashCode() { return Arrays.hashCode( new Object[] {
        this.estado, this.obs, this.orcamento, this.custoFinal, this.prestacao, this.dataInicial,
        this.dataFinal, this.funcionarioRegistou, this.funcionarioEncerrou, this.tarefa,
        this.voluntarios, this.candidatura, this.nrecibos });
    }
           
}

