package festivos.api.dominio.dtos;


public class FestivoDto {
    
    private int id;
    private String nombre;
    private int dia;
    private int mes;
    private int diasPascua;
    
    
    private int paisId;
    private String paisNombre;
    
    
    private int tipoId;
    private String tipoNombre;
    
    
    public FestivoDto() {
    }
    
    public FestivoDto(int id, String nombre, int dia, int mes, int diasPascua, 
                     int paisId, String paisNombre, int tipoId, String tipoNombre) {
        this.id = id;
        this.nombre = nombre;
        this.dia = dia;
        this.mes = mes;
        this.diasPascua = diasPascua;
        this.paisId = paisId;
        this.paisNombre = paisNombre;
        this.tipoId = tipoId;
        this.tipoNombre = tipoNombre;
    }
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getDia() {
        return dia;
    }
    
    public void setDia(int dia) {
        this.dia = dia;
    }
    
    public int getMes() {
        return mes;
    }
    
    public void setMes(int mes) {
        this.mes = mes;
    }
    
    public int getDiasPascua() {
        return diasPascua;
    }
    
    public void setDiasPascua(int diasPascua) {
        this.diasPascua = diasPascua;
    }
    
    public int getPaisId() {
        return paisId;
    }
    
    public void setPaisId(int paisId) {
        this.paisId = paisId;
    }
    
    public String getPaisNombre() {
        return paisNombre;
    }
    
    public void setPaisNombre(String paisNombre) {
        this.paisNombre = paisNombre;
    }
    
    public int getTipoId() {
        return tipoId;
    }
    
    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }
    
    public String getTipoNombre() {
        return tipoNombre;
    }
    
    public void setTipoNombre(String tipoNombre) {
        this.tipoNombre = tipoNombre;
    }
}