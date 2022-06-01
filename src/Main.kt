import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class SmartDevice(val name: String, val category: String) {

    //TODO: verificar um jeito de aplicar esse construtor
/*
    constructor(name: String, category: String, statusCode: Int) : this(name, category) {
        deviceStatus = when (statusCode) {
            0 -> "offline"
            1 -> "online"
            else -> "unknown"
        }
    }
*/

    open val deviceType = "Mr. Wednesday"

    var deviceStatus = "on"
        protected set

    //TODO: não entendi pq temos que colocar open aqui
    open fun turnOn() {
        deviceStatus = "on"
    }
    //TODO: não entendi pq temos que colocar open aqui
    open fun turnOff() {
        deviceStatus = "off"
    }
    //TODO: Desafio 1
    fun printDeviceInfo(){
        println("Device name: $name, category: $category, type: $deviceType")
    }
}

open class SmartTvDevice (deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart TV"

    private var speakerVolume by RangeRegulator(2,0,100)
    private var channelNumber by RangeRegulator(2,0,24)

/*
    private var speakerVolume = 2
        set(value) {
            if (value in 0..100) {
                field = value
            }
        }
*/
/*
    private var channelNumber = 1
        set(value) {
            if(value in 0..24) {
                field = value
            }
        }
*/

    //TODO: não entendi pq temos que colocar open e super aqui e o mesmo vale para proximos comportamentos semeplantes
    override fun turnOn() {
        super.turnOn()
        deviceStatus = "on"
        speakerVolume = 100
        channelNumber = 13
        println("$name is turned on. \nSpeaker volume is set to $speakerVolume. \nChannel number is set to $channelNumber.\n")
    }

    override fun turnOff() {
        super.turnOff()
        deviceStatus = "off"
        println("$name turned off.")
    }

    fun increaseSpeakerVolume() {
        speakerVolume++
        println("Speaker volume increased to $speakerVolume.")
    }

    //TODO: Desafio 2
    fun decreaseSpeakerVolume() {
        speakerVolume--
        println("Speaker volume decreased to $speakerVolume.")
    }

    fun nextChannel() {
        channelNumber++
        println("Channel number increased to $channelNumber.")
    }
    //TODO: Desafio 2
    fun previousChannel() {
        channelNumber--
        println("Channel number decreased to $channelNumber.")
    }
}

class SmartLightDevice (deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart Light"
    private var brightnessLevel by RangeRegulator(0, 0, 100)

/*
    private var brightnessLevel = 0
        set(value) {
            if (value in 0..100) {
                field = value
            }
        }
*/

    override fun turnOn() {
        super.turnOn()
        deviceStatus = "on"
        brightnessLevel = 42
        println("$name turned on. \nThe brightness level is $brightnessLevel.")
    }

    override fun turnOff() {
        super.turnOff()
        deviceStatus = "off"
        brightnessLevel = 0
        println("$name turned off.")
    }

    fun increaseBrightness() {
        brightnessLevel++
        println("Brightness increased to $brightnessLevel.")
    }
    //TODO: Desafio 3
    fun decreaseBrightness() {
        brightnessLevel--
        println("Brightness decreased to $brightnessLevel.")
    }
}

//TODO: Desafio 4
class SmartHome (
    private val smartTvDevice: SmartTvDevice,
    private val smartLightDevice: SmartLightDevice
    ) {

    var deviceTurnOnCount = 0
        private set

    fun turnOnTv() {
        deviceTurnOnCount++
        smartTvDevice.turnOn()
    }

    fun turnOffTv() {
        deviceTurnOnCount--
        smartTvDevice.turnOff()
    }

    fun increaseTvVolume() {
        smartTvDevice.increaseSpeakerVolume()
    }

    //TODO: Desafio 5
    fun decreaseTvVolume() {
        smartTvDevice.decreaseSpeakerVolume()
    }

    fun changeTvChannelToNext() {
        smartTvDevice.nextChannel()
    }

    //TODO: Desafio 5
    fun changeTvChannelToPrevious() {
        smartTvDevice.previousChannel()
    }

    fun turnOnLight() {
        deviceTurnOnCount++
        smartLightDevice.turnOn()
    }

    fun turnOffLight() {
        deviceTurnOnCount--
        smartLightDevice.turnOff()
    }

    fun increaseLightBrightness() {
        smartLightDevice.increaseBrightness()
    }

    //TODO: Desafio 5
    fun decreaseLightBrightness() {
        smartLightDevice.decreaseBrightness()
    }

    //TODO: Desafio 5
    fun printSmartTvInfo() {
        //TODO: Desafio 6
        smartTvDevice.printDeviceInfo()
    }

    //TODO: Desafio 5
    fun printSmartLightInfo() {
        //TODO: Desafio 6
        smartLightDevice.printDeviceInfo()
    }

    fun turnOffAllDevices() {
        turnOffLight()
        turnOffTv()
    }
}

class RangeRegulator(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int
) : ReadWriteProperty<Any?, Int> {

    private var fieldData = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if (value in minValue..maxValue){
            fieldData = value
        }
    }
}

fun main() {
    val smartHome = SmartHome(
        SmartTvDevice("Android TV", "Entertainment"),
        SmartLightDevice("Google Light", "Utility")
    )
    //TODO: Desafio 4
    /*Na classe SmartHome, faça com que todas as ações possam ser realizadas apenas quando
    a propriedade deviceStatus de cada dispositivo estiver definida como uma string "on".
    Além disso, verifique se a propriedade deviceTurnOnCount foi atualizada corretamente.*/
    smartHome.printSmartTvInfo()
    smartHome.turnOnTv()

    smartHome.printSmartLightInfo()
    smartHome.turnOnLight()

    println("\nTotal number of devices currently turned on: ${smartHome.deviceTurnOnCount}.\n")

    smartHome.printSmartTvInfo()
    smartHome.increaseTvVolume()
    smartHome.changeTvChannelToNext()
    println()
    smartHome.printSmartLightInfo()
    smartHome.increaseLightBrightness()
    println()
    smartHome.printSmartTvInfo()
    smartHome.decreaseTvVolume()
    smartHome.changeTvChannelToPrevious()
    println()
    smartHome.printSmartLightInfo()
    smartHome.decreaseLightBrightness()
    println()
    smartHome.turnOffAllDevices()
    println("\nTotal number of devices currently turned on: ${smartHome.deviceTurnOnCount}.")
}