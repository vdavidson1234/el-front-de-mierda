package com.servify.solicitudes.application.port.in;

import com.servify.solicitudes.application.dto.EmitirContraofertaCommand;

public interface EmitirContraofertaUseCase {

    void emitir(EmitirContraofertaCommand command);
}