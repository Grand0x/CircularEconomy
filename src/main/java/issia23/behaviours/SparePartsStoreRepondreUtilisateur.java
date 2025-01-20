package issia23.behaviours;

import jade.proto.ContractNetResponder;
import issia23.agents.SparePartsStoreAgent;
import issia23.data.Part;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
public class SparePartsStoreRepondreUtilisateur extends ContractNetResponder {

    SparePartsStoreAgent monAgent;

    private static final int MIN_PRICE = 20;
    private static final int MAX_PRICE = 40;

    public SparePartsStoreRepondreUtilisateur(SparePartsStoreAgent a, MessageTemplate model) {
        super(a, model);
        monAgent = a;
    }

    // Fonction déclenchée par un message CFP : répondre à la demande d'une pièce détachée
    @Override
    protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
        monAgent.println("~".repeat(40));
        ACLMessage answer = cfp.createReply();

        try {
            // Le message CFP contient une référence de la pièce demandée (nom)
            String partName = cfp.getContent();
            monAgent.println(cfp.getSender().getLocalName() + " demande une pièce détachée: " + partName);

            // Recherche de la pièce dans l'inventaire du magasin
            Part requestedPart = monAgent.findPart(partName);

            if (requestedPart == null) {
                // Si la pièce n'est pas disponible, on refuse la demande
                monAgent.println("Désolé, je n'ai pas cette pièce.");
                answer.setPerformative(ACLMessage.REFUSE);
                return answer;
            }

            // Calcul du prix (entre 20 et 40 €)
            int price = MIN_PRICE + (int) (Math.random() * (MAX_PRICE - MIN_PRICE + 1));

            // Réponse avec le prix proposé pour la pièce
            answer.setPerformative(ACLMessage.PROPOSE);
            answer.setContent(String.valueOf(price));  // Le prix proposé pour la pièce

            monAgent.println("Je propose un prix de " + price + " € pour la pièce détachée.");

            return answer;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Fonction déclenchée par un message ACCEPT_PROPOSAL : l'utilisateur accepte l'offre
    @Override
    protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
        monAgent.println("=".repeat(15));
        monAgent.println("J'ai proposé " + propose.getContent());
        monAgent.println(cfp.getSender().getLocalName() + " a accepté ma proposition avec la réponse : " + accept.getContent());

        ACLMessage msg = accept.createReply();
        msg.setPerformative(ACLMessage.INFORM);
        msg.setContent("Pièce détachée acceptée et réservée !");
        return msg;
    }

    // Fonction déclenchée par un message REJECT_PROPOSAL : l'utilisateur rejette l'offre
    @Override
    protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
        monAgent.println("=".repeat(10));
        monAgent.println("PROPOSITION REJETEE");
        monAgent.println(cfp.getSender().getLocalName() + " a rejeté ma proposition : " + reject.getContent());
    }

}

