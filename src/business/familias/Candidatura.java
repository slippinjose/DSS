package business.familias;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 30.12.2014
 */

class Candidatura implements ICandidatura{
 
    private int nr;
    private GregorianCalendar dataSubmissao;
    private String estado; // "Em análise", "Aprovada", "Reprovada"
    private GregorianCalendar dataDecisao;
    private int funcionarioRegistou; // id do fucionário
    private int funcionarioAprovou;  // id do fucionário
    private List<Integer> membros;
    private int representante;
    private String descricao;
    
    // Construtores
    /**
     * 
     * @param nr
     * @param dataSub
     * @param estado
     * @param dataDecisao
     * @param funcionarioRegistou
     * @param funcionarioAprovou
     * @param membros
     * @param representante
     * @param descricao 
     */   
    public Candidatura(int nr, GregorianCalendar dataSub, String estado, GregorianCalendar dataDecisao, int funcionarioRegistou, int funcionarioAprovou, List<Integer> membros, int representante, String descricao) {
       this.nr = nr;
       this.dataSubmissao=dataSub;
       this.dataDecisao=dataDecisao;
       this.estado = estado;
       this.funcionarioRegistou = funcionarioRegistou;
       this.funcionarioAprovou = funcionarioAprovou;
       this.membros = membros;
       this.representante = representante;
       this.descricao = descricao;
    }

    public Candidatura() {
       this.nr =0;
       this.dataSubmissao = new GregorianCalendar();
       this.dataDecisao = new GregorianCalendar();
       this.estado ="";
       this.funcionarioRegistou =-1;
       this.funcionarioAprovou =-1;
       this.membros = new ArrayList<>();
       this.representante = -1;
       this.descricao="";
    }

    public Candidatura(Candidatura c) {
       this.nr =c.getNr();
       this.dataSubmissao = c.getDataSubmissao();
       this.dataDecisao = c.getDataDecisao();
       this.estado=c.getEstado();
       this.funcionarioRegistou =c.getFuncionarioRegistou();
       this.funcionarioAprovou =c.getFuncionarioAprovou();
       this.membros = c.getMembros();
       this.representante = c.getRepresentante();
       this.descricao=c.getDescricao();
    }

    // Get`s e Set`s
    @Override
    public GregorianCalendar getDataSubmissao() {    
        return dataSubmissao;
    }

    @Override
    public void setDataSubmissao(GregorianCalendar dataSubmissao) {
        this.dataSubmissao = dataSubmissao;
    }

    @Override
    public int getNr() {
        return nr;
    }

    @Override
    public void setNr(int nr) {
        this.nr = nr;
    }
    
    @Override
    public GregorianCalendar getDataDecisao() {
        return dataDecisao;
    }

    @Override
    public void setDataDecisao(GregorianCalendar dataDecisao) {
        this.dataDecisao = dataDecisao;
    }

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int getFuncionarioRegistou() {
        return funcionarioRegistou;
    }

    @Override
    public void setFuncionarioRegistou(int funcionarioRegistou) {
        this.funcionarioRegistou = funcionarioRegistou;
    }

    @Override
    public int getFuncionarioAprovou() {
        return funcionarioAprovou;
    }

    @Override
    public void setFuncionarioAprovou(int funcionarioAprovou) {
        this.funcionarioAprovou = funcionarioAprovou;
    }

    @Override
    public List<Integer> getMembros() {
        return membros;
    }

    @Override
    public void setMembros(List<Integer> membros) {
        this.membros = membros;
    }

    @Override
    public int getRepresentante() {
        return representante;
    }

    @Override
    public void setRepresentante(int representante) {
        this.representante = representante;
    }
    
    @Override
     public String getDescricao() {
        return descricao;
    }

    @Override
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
// Clone
    @Override
    public Candidatura clone(){
        return new Candidatura(this);
    }

// Equals
    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true; // era este stat que tinhas mal
        
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        else{
            Candidatura r = (Candidatura) obj; 
             
             for(int f : this.membros){
                if(!r.getMembros().contains(f)) return false;
            }
        return( this.nr == r.getNr()&&
                this.dataSubmissao.equals(r.getDataSubmissao()) &&
                this.estado.equals(r.getEstado()) && this.membros.equals(r.getMembros()) &&
                this.funcionarioRegistou==r.getFuncionarioRegistou() && 
                this.funcionarioAprovou==r.getFuncionarioAprovou() && 
                this.representante==r.getRepresentante() && this.descricao.equals(r.getDescricao()) );
        }
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {this.nr,
        this.estado, this.membros, this.funcionarioRegistou, this.funcionarioAprovou, this.representante,this.descricao});
    }
    
    /* adicionar membro*/
    @Override
    public void add(int m){
        this.membros.add(m);
    }
    
    /*remover membro*/
    @Override
    public void rem(int m){
        this.membros.remove(m);
    }
}
