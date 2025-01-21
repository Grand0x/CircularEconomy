package issia23.agents;

import issia23.behaviours.DistributorRepondreUtilisateur;
import issia23.data.Product;
import jade.core.AgentServicesTools;
import jade.gui.AgentWindowed;
import jade.gui.SimpleWindow4Agent;
import jade.lang.acl.MessageTemplate;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DistributorAgent extends AgentWindowed {
    Set<Product> products;

    @Override
    public void setup(){
        this.window = new SimpleWindow4Agent(getLocalName(),this);
        this.window.setBackgroundTextColor(Color.GRAY);
        println("hello, do you want a new object  ?");

        //registration to the yellow pages (Directory Facilitator Agent)
        AgentServicesTools.register(this, "repair", "distributor");
        println("I'm just registered as a Distributor");

        products = new HashSet<>();
        products.addAll(Product.getListProducts());
        println("i have the following products : ");
        for(var p:products) println(p.getName() + " ");

        addListeningACFP();
    }

    private void addListeningACFP()
    {

        MessageTemplate model = MessageTemplate.MatchConversationId("id");

        var attenteDemandeUtilisateur = new DistributorRepondreUtilisateur(this, model);

        addBehaviour(attenteDemandeUtilisateur);
    }

    public Product findProduct(String productName) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }



    public void println(String s){
        window.println(s);
    }

}
