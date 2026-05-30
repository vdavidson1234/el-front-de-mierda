package com.servify.solicitudes.application.port.in;

import com.servify.solicitudes.application.dto.ContraofertaResult;
import com.servify.solicitudes.application.dto.ResolverContraofertaCommand;

public interface ResolverContraofertaUseCase {

    ContraofertaResult resolver(ResolverContraofertaCommand command);
}
