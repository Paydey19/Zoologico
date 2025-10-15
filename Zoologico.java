/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package zoologico;

/**
 *
 * @author estudiante
 */
    
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Zoologico {

    private String nombre;
    private ArrayList<Animal> listaAnimales;
    private final int CAPACIDAD_MAXIMA = 100;
    private Scanner sc;

    public Zoologico() {
        listaAnimales = new ArrayList<>();
        sc = new Scanner(System.in);
    }

    public void agregarAnimal() {
        System.out.println("\n--- AGREGAR ANIMAL ---");
        System.out.println("Tipo de animal: 1) Mamífero  2) Ave  3) Pez");
        int tipo = leerEntero();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Especie: ");
        String especie = sc.nextLine();
        System.out.print("Edad: ");
        int edad = leerEntero();

        switch (tipo) {
            case 1:
                System.out.print("Tipo de pelo: ");
                String tipoPelo = sc.nextLine();
                System.out.print("¿Es doméstico? (true/false): ");
                boolean domestico = Boolean.parseBoolean(sc.nextLine());
                listaAnimales.add(new Mamifero(nombre, especie, edad, tipoPelo, domestico));
                break;

            case 2:
                System.out.print("Color de plumas: ");
                String color = sc.nextLine();
                System.out.print("¿Puede volar? (true/false): ");
                boolean puedeVolar = Boolean.parseBoolean(sc.nextLine());
                listaAnimales.add(new Ave(nombre, especie, edad, color, puedeVolar));
                break;

            case 3:
                System.out.print("Tipo de agua (dulce/salada): ");
                String tipoAgua = sc.nextLine();
                System.out.print("¿Tiene escamas? (true/false): ");
                boolean tieneEscamas = Boolean.parseBoolean(sc.nextLine());
                listaAnimales.add(new Pez(nombre, especie, edad, tipoAgua, tieneEscamas));
                break;

            default:
                System.out.println("Tipo inválido.");
                break;
        }
    }

    public void mostrarAnimales() {
        System.out.println("\n--- LISTA DE ANIMALES ---");
        if (listaAnimales.isEmpty()) {
            System.out.println("No hay animales registrados.");
            return;
        }
        int i = 1;
        for (Animal a : listaAnimales) {
            System.out.println(i + ". " + a);
            i++;
        }
    }

    public void eliminarAnimal() {
        mostrarAnimales();
        if (listaAnimales.isEmpty()) return;

        System.out.print("Ingrese el número del animal a eliminar: ");
        int indice = leerEntero();

        if (indice >= 1 && indice <= listaAnimales.size()) {
            Animal eliminado = listaAnimales.remove(indice - 1);
            System.out.println("Animal eliminado: " + eliminado.getNombre());
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public void guardarAnimales() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("animales.txt"))) {
            for (Animal a : listaAnimales) {
                if (a instanceof Mamifero) {
                    Mamifero m = (Mamifero) a;
                    pw.println(m.getNombre() + "," + m.getEspecie() + "," + m.getEdad() + "," +
                            m.getTipoPelo() + "," + m.isDomestico() + ",Mamifero");
                } else if (a instanceof Ave) {
                    Ave av = (Ave) a;
                    pw.println(av.getNombre() + "," + av.getEspecie() + "," + av.getEdad() + "," +
                            av.getColorPlumas() + "," + av.isPuedeVolar() + ",Ave");
                } else if (a instanceof Pez) {
                    Pez pz = (Pez) a;
                    pw.println(pz.getNombre() + "," + pz.getEspecie() + "," + pz.getEdad() + "," +
                            pz.getTipoAgua() + "," + pz.isTieneEscamas() + ",Pez");
                }
            }
            System.out.println("Animales guardados en 'animales.txt'.");
        } catch (IOException e) {
            System.out.println("Error al guardar los animales.");
        }
    }

    public void cargarAnimales() {
        listaAnimales.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("animales.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String tipo = datos[datos.length - 1];
                if (tipo.equalsIgnoreCase("Mamifero")) {
                    listaAnimales.add(new Mamifero(datos[0], datos[1],
                            Integer.parseInt(datos[2]), datos[3],
                            Boolean.parseBoolean(datos[4])));
                } else if (tipo.equalsIgnoreCase("Ave")) {
                    listaAnimales.add(new Ave(datos[0], datos[1],
                            Integer.parseInt(datos[2]), datos[3],
                            Boolean.parseBoolean(datos[4])));
                } else if (tipo.equalsIgnoreCase("Pez")) {
                    listaAnimales.add(new Pez(datos[0], datos[1],
                            Integer.parseInt(datos[2]), datos[3],
                            Boolean.parseBoolean(datos[4])));
                }
            }
            System.out.println("Animales cargados desde 'animales.txt'.");
        } catch (FileNotFoundException e) {
            System.out.println("No existe el archivo 'animales.txt'.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }
    }

    private int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida, intente de nuevo: ");
            }
        }
    }

    public static void main(String[] args) {
        Zoologico zoo = new Zoologico();
        Scanner scMain = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== MENÚ DEL ZOOLÓGICO =====");
            System.out.println("1. Agregar animal");
            System.out.println("2. Mostrar animales");
            System.out.println("3. Guardar animales");
            System.out.println("4. Cargar animales");
            System.out.println("5. Eliminar animal");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = zoo.leerEntero();

            switch (opcion) {
                case 1:
                    zoo.agregarAnimal();
                    break;
                case 2:
                    zoo.mostrarAnimales();
                    break;
                case 3:
                    zoo.guardarAnimales();
                    break;
                case 4:
                    zoo.cargarAnimales();
                    break;
                case 5:
                    zoo.eliminarAnimal();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        } while (opcion != 0);
        scMain.close();
    }
}



class Animal {
    private String nombre;
    private String especie;
    private int edad;

    public Animal(String nombre, String especie, int edad) {
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
    }

    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }
    public int getEdad() { return edad; }

    @Override
    public String toString() {
        return nombre + " | " + especie + " | " + edad + " años";
    }
}

class Mamifero extends Animal {
    private String tipoPelo;
    private boolean domestico;

    public Mamifero(String nombre, String especie, int edad, String tipoPelo, boolean domestico) {
        super(nombre, especie, edad);
        this.tipoPelo = tipoPelo;
        this.domestico = domestico;
    }

    public String getTipoPelo() { return tipoPelo; }
    public boolean isDomestico() { return domestico; }

    @Override
    public String toString() {
        return "[Mamífero] " + super.toString() + " | Tipo de pelo: " + tipoPelo + " | Doméstico: " + domestico;
    }
}

class Ave extends Animal {
    private String colorPlumas;
    private boolean puedeVolar;

    public Ave(String nombre, String especie, int edad, String colorPlumas, boolean puedeVolar) {
        super(nombre, especie, edad);
        this.colorPlumas = colorPlumas;
        this.puedeVolar = puedeVolar;
    }

    public String getColorPlumas() { return colorPlumas; }
    public boolean isPuedeVolar() { return puedeVolar; }

    @Override
    public String toString() {
        return "[Ave] " + super.toString() + " | Color de plumas: " + colorPlumas + " | Puede volar: " + puedeVolar;
    }
}

class Pez extends Animal {
    private String tipoAgua;
    private boolean tieneEscamas;

    public Pez(String nombre, String especie, int edad, String tipoAgua, boolean tieneEscamas) {
        super(nombre, especie, edad);
        this.tipoAgua = tipoAgua;
        this.tieneEscamas = tieneEscamas;
    }

    public String getTipoAgua() { return tipoAgua; }
    public boolean isTieneEscamas() { return tieneEscamas; }

    @Override
    public String toString() {
        return "[Pez] " + super.toString() + " | Tipo de agua: " + tipoAgua + " | Escamas: " + tieneEscamas;
    }
}
