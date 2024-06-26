package parsers;

import model.Employee;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StaxParserDemo {
  public static void main(String[] args) throws XMLStreamException {
    List<Employee> empList = null;
    Employee currEmp = null;
    String tagContent = null;
    XMLInputFactory factory = XMLInputFactory.newInstance();
    XMLStreamReader reader =
        factory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream("employees.xml"));

    while (reader.hasNext()) {
      int event = reader.next();

      switch (event) {
        case XMLStreamConstants.START_ELEMENT:
          if ("employee".equals(reader.getLocalName())) {
            currEmp = new Employee();
            currEmp.id = reader.getAttributeValue(0);
          }
          if ("employees".equals(reader.getLocalName())) {
            empList = new ArrayList<>();
          }
          break;

        case XMLStreamConstants.CHARACTERS:
          tagContent = reader.getText().trim();
          break;

        case XMLStreamConstants.END_ELEMENT:
					switch (reader.getLocalName()) {
						case "employee" -> {
							empList.add(currEmp);
						}
						case "firstName" -> currEmp.firstName = tagContent;
						case "lastName" -> currEmp.lastName = tagContent;
						case "location" -> currEmp.location = tagContent;
					}
          break;

        case XMLStreamConstants.START_DOCUMENT:
          empList = new ArrayList<>();
          break;
      }

    }

    //Print the employee list populated from XML
    for (Employee emp : empList) {
      System.out.println(emp);
    }

  }
}
