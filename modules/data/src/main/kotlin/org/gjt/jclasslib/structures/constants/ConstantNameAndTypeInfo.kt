/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.structures.constants

import org.gjt.jclasslib.structures.CPInfo
import org.gjt.jclasslib.structures.ConstantType
import org.gjt.jclasslib.structures.InvalidByteCodeException

import java.io.DataInput
import java.io.DataOutput
import java.io.IOException

/**
 * Describes a CONSTANT_NameAndType_info constant pool data structure.

 * @author [Ingo Kegel](mailto:jclasslib@ej-technologies.com)
 */
class ConstantNameAndTypeInfo : CPInfo() {

    /**
     * Index of the constant pool entry containing the name of this entry.
     */
    var nameIndex: Int = 0

    /**
     * Index of the constant pool entry containing the descriptor of this entry.
     */
    var descriptorIndex: Int = 0

    override val constantType: ConstantType
        get() = ConstantType.NAME_AND_TYPE

    override val verbose: String
        @Throws(InvalidByteCodeException::class)
        get() = name + descriptor

    /**
     * The name of this entry.
     */
    val name: String
        @Throws(InvalidByteCodeException::class)
        get() = classFile.getConstantPoolEntryName(nameIndex)

    /**
     * The descriptor string.
     */
    val descriptor: String
        @Throws(InvalidByteCodeException::class)
        get() = classFile.getConstantPoolEntryName(descriptorIndex)

    @Throws(InvalidByteCodeException::class, IOException::class)
    override fun read(input: DataInput) {

        nameIndex = input.readUnsignedShort()
        descriptorIndex = input.readUnsignedShort()

        debugRead()
    }

    @Throws(InvalidByteCodeException::class, IOException::class)
    override fun write(output: DataOutput) {

        output.writeByte(ConstantType.NAME_AND_TYPE.tag)
        output.writeShort(nameIndex)
        output.writeShort(descriptorIndex)
        debugWrite()
    }

    override val debugInfo: String
        get() = "with nameIndex $nameIndex and descriptorIndex $descriptorIndex"

    override fun equals(other: Any?): Boolean {
        if (other !is ConstantNameAndTypeInfo) {
            return false
        }
        return super.equals(other) && other.nameIndex == nameIndex && other.descriptorIndex == descriptorIndex
    }

    override fun hashCode(): Int = super.hashCode() xor nameIndex xor descriptorIndex

}