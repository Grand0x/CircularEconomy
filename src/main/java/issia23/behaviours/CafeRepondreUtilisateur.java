package issia23.behaviours;

import issia23.agents.RepairCoffeeAgent;
import issia23.data.Product;
import issia23.data.Part;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetResponder;

import java.util.*;

public class CafeRepondreUtilisateur extends ContractNetResponder {

    RepairCoffeeAgent monAgent;

    private static final int MIN_PRICE = 5;
    private static final int MAX_PRICE = 15;

    public CafeRepondreUtilisateur(RepairCoffeeAgent a, MessageTemplate model) {
        super(a, model);
        monAgent = a;
    }

    //function triggered by a PROPOSE msg : send back the ranking
    @Override
    protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
        monAgent.println("~".repeat(40));
        ACLMessage answer = cfp.createReply();

        try {
            Product productDTO = (Product) cfp.getContentObject();
            monAgent.println(cfp.getSender().getLocalName() + " demande une réparation pour le produit de type " + productDTO.getName());

            // Le contenu du message CFP contient la référence de la pièce demandée
            String partName = cfp.getContent();
            monAgent.println(cfp.getSender().getLocalName() + " asks for part: " + partName);


            // Recherche de la pièce dans la liste des pièces disponibles
            Part partToRepair = productDTO.getFaultyPart();

            if (!monAgent.parts.contains(partToRepair)) {
                // Si la pièce n'est pas disponible, on refuse la demande
                monAgent.println("Sorry, I don't have this part.");
                answer.setPerformative(ACLMessage.REFUSE);
                return answer;
            }

            // Calcul du prix (entre 5 et 15 €)
            int price = MIN_PRICE + (int) (Math.random() * (MAX_PRICE - MIN_PRICE + 1));

            // Réponse avec le prix proposé
            answer.setPerformative(ACLMessage.PROPOSE);
            answer.setContent(String.valueOf(price));  // Le prix proposé pour la réparation

            monAgent.println("Je propose un prix de " + price + " € pour la réparation.");

            return answer;
        } catch (UnreadableException e) {
            throw new RuntimeException(e);
        }
    }

    //function triggered by a ACCEPT_PROPOSAL msg : the polling station agent  accept the vote
    //@param cfp : the initial cfp message
    //@param propose : the proposal I sent
    //@param accept : the acceptation sent by the auctioneer
    @Override
    protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
        monAgent.println("=".repeat(15));
        monAgent.println("J'ai proposé " + propose.getContent());
        monAgent.println(cfp.getSender().getLocalName() + " a accepté ma proposition avec la réponse : " + accept.getContent());

        ACLMessage msg = accept.createReply();
        msg.setPerformative(ACLMessage.INFORM);
        msg.setContent("Réparation acceptée !");
        return msg;
    }

    //function triggered by a REJECT_PROPOSAL msg : the auctioneer rejected my vote !
    //@param cfp : the initial cfp message
    //@param propose : the proposal I sent
    //@param accept : the reject sent by the auctioneer
    @Override
    protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
        monAgent.println("=".repeat(10));
        monAgent.println("PROPOSITION REJETEE");
        monAgent.println(cfp.getSender().getLocalName() + " a rejeté ma proposition : " + reject.getContent());
    }


};
