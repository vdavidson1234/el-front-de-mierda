package com.servify.solicitudes.application.dto;

import java.util.UUID;

public class ResolverContraofertaCommand {

    private UUID contraofertaId;
    private UUID solicitanteId;
    private TipoDecisionSolicitud decision;

    public ResolverContraofertaCommand() {
    }

    public ResolverContraofertaCommand(UUID contraofertaId,
                                       UUID solicitanteId,
                                       TipoDecisionSolicitud decision) {
        this.contraofertaId = contraofertaId;
        this.solicitanteId = solicitanteId;
        this.decision = decision;
    }

    public UUID getContraofertaId() {
        return contraofertaId;
    }

    public UUID getSolicitanteId() {
        return solicitanteId;
    }

    public TipoDecisionSolicitud getDecision() {
        return decision;
    }
}
