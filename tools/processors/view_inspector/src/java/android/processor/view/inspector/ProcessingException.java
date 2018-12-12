/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.processor.view.inspector;

import static javax.tools.Diagnostic.Kind.ERROR;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;

/**
 * Internal exception used to signal an error processing an annotation.
 */
final class ProcessingException extends RuntimeException {
    private final Element mElement;
    private final AnnotationMirror mAnnotationMirror;
    private final AnnotationValue mAnnotationValue;

    ProcessingException(String message) {
        this(message, null, null, null);
    }

    ProcessingException(String message, Element element) {
        this(message, element, null, null);
    }

    ProcessingException(String message, Element element, AnnotationMirror annotationMirror) {
        this(message, element, annotationMirror, null);
    }

    ProcessingException(
            String message,
            Element element,
            AnnotationMirror annotationMirror,
            AnnotationValue annotationValue) {
        super(message);
        mElement = element;
        mAnnotationMirror = annotationMirror;
        mAnnotationValue = annotationValue;
    }

    /**
     * Prints the exception to a Messager.
     *
     * @param messager A Messager to print to
     */
    void print(Messager messager) {
        if (mElement != null) {
            if (mAnnotationMirror != null) {
                if (mAnnotationValue != null) {
                    messager.printMessage(
                            ERROR, getMessage(), mElement, mAnnotationMirror, mAnnotationValue);
                } else {
                    messager.printMessage(ERROR, getMessage(), mElement, mAnnotationMirror);
                }
            } else {
                messager.printMessage(ERROR, getMessage(), mElement);
            }
        } else {
            messager.printMessage(ERROR, getMessage());
        }
    }
}
