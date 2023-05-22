package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		System.out.println();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			List<Sale> sales = new ArrayList<>();

			String line = br.readLine();
			while (line != null) {

				String[] fields = line.split(",");
				sales.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2], Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}

			Comparator<Sale> comp = (s1, s2) -> s1.averagePrice().compareTo(s2.averagePrice());

			List<Sale> topFiveSales = sales.stream().filter(s -> s.getYear() == 2016).sorted(comp.reversed()).limit(5).collect(Collectors.toList());

			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			topFiveSales.forEach(System.out::println);

			System.out.println();

			double sum = sales.stream().filter(s -> (s.getSeller().toUpperCase().equals("LOGAN")) && (s.getMonth() == 1 || s.getMonth() == 7)).map(s -> (s.getTotal())).reduce(0.0, (x, y) -> x + y);
			System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f", sum));
		} 
		catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}

		sc.close();
	}

}
