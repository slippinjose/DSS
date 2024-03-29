package data_access;


import business.doacoes.IDoador;
import business.doacoes.DoadorFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Implementação de um Data Acess Object para gerir instancias da classe Doador.
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2015.01.04
 */
class DoadorDAO implements Map<String,IDoador>{
    
    public Connection conn;
    public MySQLParseTools parseTools;
    
    /**
     * Construtor que fornece conexão à tabela Doadores na base de dados da Habitat
     * @throws ConnectionErrorException 
     */
    public DoadorDAO () throws ConnectionErrorException {
        try {
            parseTools = new MySQLParseTools();
            this.conn = (new MySQLManager()).getConnection();
        } catch (SQLException e) {System.out.println ("error_doador_bd");}
    }
    
    @Override
    public void clear() {
        try {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Doadores WHERE Id>0;");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    @Override
    public boolean containsKey(Object key) {
        try {
            Integer chave = Integer.parseInt ((String)key);
            Statement stm = conn.createStatement();
            String sql = "SELECT Nome FROM Doadores WHERE NIF='"+key+"'";
            ResultSet rs = stm.executeQuery(sql);
            return rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    @Override
    public boolean containsValue(Object value) {
        try {
            if(value instanceof IDoador) {
            
                IDoador d = (IDoador)value;
                Statement stm = conn.createStatement();
                
                Set<String> chaves = this.keySet();
                // Percorrer tabela de doadores e verificar se existe doador
                // exatamente igual
                
                for(String s: chaves){
                    IDoador ido = this.get(s);
                    if(ido!=null){
                        if(d.equals(ido)) return true;
                    }
                }
                return false;
            } else return false;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    @Override
    public Set<Entry<String, IDoador>> entrySet() {
        try {
            Set<Entry<String,IDoador>> set;
            Set<String> chaves = this.keySet();
            HashMap<String,IDoador> map = new HashMap<>();
            for(String s : chaves)
                    map.put(s,this.get(s));
               
            return map.entrySet();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public IDoador get(Object key) {
        IDoador d = new DoadorFactory().createDoador();
        
        try {
            Integer chave = Integer.parseInt((String)key);
            Statement stm = conn.createStatement();
            int nrDoador;
            String sql = "SELECT * FROM Doadores WHERE NIF='"+chave+"'";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()){
                d = new DoadorFactory().createDoador();
                nrDoador = (rs.getInt("Id"));
                d.setNIF(Integer.toString(rs.getInt("NIF")));
                d.setTelefone(rs.getString("Telefone"));
                d.setTelemovel(rs.getString("Telemovel"));
                d.setLocalidade(rs.getString("Localidade"));
                d.setRua(rs.getString("Rua"));
                d.setCodPostal(rs.getString("CodigoPostal"));
                d.setNotas(rs.getString("Obs"));
                d.setPessoaContato(rs.getString("PessoaContato"));
                d.setEmail(rs.getString("Email"));
                d.setSite(rs.getString("Website"));
                d.setTipo(rs.getString("Tipo"));
                
                Set<Integer> donativos = new HashSet<>();
                sql = "Select Donativo from ProjetoDoadoresDonativos WHERE Doador='"+nrDoador+"'";
                rs = stm.executeQuery(sql);
                while (rs.next())
                    donativos.add(rs.getInt("Donativo"));
                d.setDonativos(donativos);
                
                return d;
            }
        } catch (Exception e) {throw new NullPointerException(e.getMessage());}
        
        return d;
    }
    
    @Override
    public boolean isEmpty() {
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT Nome FROM Doadores");
            return !rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    
    @Override
    public Set<String> keySet() {
        try {
            Set<String> set = new HashSet<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm  .executeQuery ("Select NIF From Doadores");
            while (rs.next())
                set.add(String.valueOf(rs.getInt(1)));
            return set;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    @Override
    public IDoador put(String key, IDoador value) {
        try {            
            Integer chave = Integer.parseInt((String)key);
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Doadores WHERE NIF='"+chave+"'");
            int i  = stm.executeUpdate(insert(key,value));
            IDoador d = new DoadorFactory().createDoador();
            d.setNIF(value.getNIF());
            d.setNome(value.getNome());
            d.setTelefone(value.getTelefone());
            d.setTelemovel(value.getTelemovel());
            d.setLocalidade(value.getLocalidade());
            d.setRua(value.getRua());
            d.setCodPostal(value.getCodPostal());
            d.setNotas(value.getNotas());
            d.setPessoaContato(value.getPessoaContato());
            d.setEmail(value.getEmail());
            d.setSite(value.getSite());
            d.setTipo(value.getTipo());
            return d;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    /*Método auxiliar de inserção na base de dados*/
    private String insert(String key, IDoador value){       
        
        
        ArrayList<Object> valores = new ArrayList<>();
        valores.add(this.generateDoadorKey());
        valores.add(value.getNIF());
        valores.add(value.getNome());
        valores.add(value.getTelefone());
        valores.add(value.getTelemovel());
        valores.add(value.getLocalidade());
        valores.add(value.getRua());
        valores.add(value.getCodPostal());
        valores.add(value.getNotas());
        valores.add(value.getPessoaContato());
        valores.add(value.getEmail());
        valores.add(value.getSite());
        valores.add(value.getTipo());
        
        String sql = parseTools.createInsert("Doadores", valores);
        return sql;
    }
    

    
    @Override
    public void putAll(Map<? extends String, ? extends IDoador> m) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override 
    public IDoador remove(Object key) {
        try {
            IDoador d = this.get((String)key);
            Integer chave = Integer.parseInt((String)key);
            
            Statement stm = conn.createStatement();
            String sql = "DELETE '"+chave+"' FROM Doadores";
            int i  = stm.executeUpdate(sql);
            return d;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    @Override
    @SuppressWarnings("empty-statement")
    public int size() {
        try {
            int i = 0;
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT Id FROM Doadores");
            for (;rs.next();i++);
            return i;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    
    @Override
    public Collection<IDoador> values() {
        try {
            Set<IDoador> set = new HashSet<>();
            Set<String> keys = new HashSet<>(this.keySet());
            for(String key : keys)
                set.add(this.get(key));
            return set;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        
        else if(this.getClass() != o.getClass()) return false;
        
        else{
            Map<String,IDoador> ddao = (DoadorDAO) o;
            
            for(IDoador d : this.values()){
                if(!ddao.containsKey(d.getNIF())) return false;
                else{
                    if(!d.equals(ddao.get(d.getNIF()))) return false;
                }
            }
            return true;
        }
    }

    @Override
    public int hashCode() {
        int hash = Arrays.hashCode(new Object[]{conn, parseTools});
        for(IDoador d : this.values())
            hash+=d.hashCode();
        return hash;
    }
    
    /**
     * Procura a maior chave de doador existente na base de dados e retorna
     * esse valor incrementado em uma unidade
     * @return Chave que identificará univocamente no sistema um doador. 
     */
    public int generateDoadorKey(){
       try {
            if(!this.isEmpty()){
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT MAX(Id) FROM Doadores;");
                if(rs.next()){
                    return(rs.getInt(1) + 1);
                }
            } else return 1;
        } catch (Exception e) {return 1;}
        
        return -1;
    }
    
    /**
     * Fechar a ligação à base de dados.
     */
    public void close(){
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DoadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
